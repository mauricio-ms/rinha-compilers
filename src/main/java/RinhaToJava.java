import antlr.RinhaBaseVisitor;
import antlr.RinhaParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.Optional;

public class RinhaToJava extends RinhaBaseVisitor<Value> {
    private final RinhaProgram rinhaProgram = new RinhaProgram();

    @Override
    public Value visitCompilationUnit(RinhaParser.CompilationUnitContext ctx) {
        rinhaProgram.setCurrentScope(new GlobalScope());
        return visitChildren(ctx);
    }

    @Override
    public Value visitTerm(RinhaParser.TermContext ctx) {
        return Optional.ofNullable(evaluateFunction(ctx))
                .or(() -> Optional.ofNullable(ctx.uop)
                        .map(uop -> evaluateUOp(uop.getText(), ctx.term(0))))
                .or(() -> Optional.ofNullable(ctx.bop)
                        .map(bop -> evaluateBinOp(bop.getText(), ctx.term(0), ctx.term(1))))
                .or(() -> Optional.ofNullable(ctx.term(0))
                        .map(this::visitTerm))
                .orElseGet(() -> visitChildren(ctx));
    }

    private Value evaluateBinOp(String bop, RinhaParser.TermContext leftExpr, RinhaParser.TermContext rightExpr) {
        Value leftValue = visitTerm(leftExpr);
        Value rightValue = visitTerm(rightExpr);
        return switch (bop) {
            case "*" -> leftValue.mul(rightValue);
            case "/" -> leftValue.div(rightValue);
            case "%" -> leftValue.rem(rightValue);
            case "+" -> leftValue.add(rightValue);
            case "-" -> leftValue.sub(rightValue);
            case "<=" -> leftValue.lte(rightValue);
            case ">=" -> leftValue.gte(rightValue);
            case "<" -> leftValue.lt(rightValue);
            case ">" -> leftValue.gt(rightValue);
            case "==" -> leftValue.eq(rightValue);
            case "&&" -> leftValue.and(rightValue);
            case "||" -> leftValue.or(rightValue);
            default -> throw new RuntimeException("Binary Operation '" + bop + "' cannot be parsed.");
        };
    }

    private Value evaluateUOp(String uop, RinhaParser.TermContext expr) {
        Value exprValue = visitTerm(expr);
        return switch (uop) {
            case "+" -> exprValue;
            case "-" -> exprValue.mul(Int.MINUS_ONE);
            default -> throw new RuntimeException("Unary Operation '" + uop + "' cannot be parsed.");
        };
    }

    private Value evaluateFunction(RinhaParser.TermContext ctx) {
        if (ctx.termList() == null) {
            return null;
        } else if (visitTerm(ctx.term(0)) instanceof Function function) {
            List<String> parameters = function.parameters();
            var expressions = Optional.ofNullable(ctx.termList())
                    .map(RinhaParser.TermListContext::term)
                    .orElseGet(List::of);
            if (parameters.size() != expressions.size()) {
                String prefixMessage = parameters.isEmpty() ? "No parameter expected" :
                        parameters.size() == 1 ? "Expected 1 parameter" :
                                "Expected " + parameters.size() + " parameters";
                throw new RuntimeException(prefixMessage + " for function '" + ctx.term(0).getText() + "' but found " + expressions.size() + ".");
            }

            rinhaProgram.setCurrentScope(new FunctionScope(
                    function.closureScope(),
                    rinhaProgram.currentScope()));
            List<Value> parameterValues = expressions.stream().map(this::visitTerm).toList();
            Value cached = rinhaProgram.readFromCache(function, parameterValues);
            if (cached != null) {
                rinhaProgram.deleteCurrentScope();
                return cached;
            }
            for (int i = 0; i < parameters.size(); i++) {
                rinhaProgram.declare(parameters.get(i), parameterValues.get(i));
            }

            Value blockReturn = visitBlock(function.block());
            boolean sideEffect = rinhaProgram.sideEffect();
            rinhaProgram.deleteCurrentScope();
            if (sideEffect) {
                rinhaProgram.markSideEffect();
            }
            rinhaProgram.cache(function, parameterValues, blockReturn);
            return blockReturn;
        } else {
            throw new RuntimeException(ctx.getText() + " is not a function.");
        }
    }

    @Override
    public Value visitBlock(RinhaParser.BlockContext ctx) {
        Value value = null;
        for (var term : ctx.term()) {
            value = visitTerm(term);
        }
        return value;
    }

    @Override
    public Value visitLet(RinhaParser.LetContext ctx) {
        String name = ctx.ID().getText();
        Value value = visitTerm(ctx.term(0));
        rinhaProgram.declare(name, value);
        return Optional.ofNullable(ctx.term(1))
                .map(this::visitTerm)
                .orElse(value);
    }

    @Override
    public Value visitFunctionDefinition(RinhaParser.FunctionDefinitionContext ctx) {
        return new Function(
                new FunctionScope(
                        rinhaProgram.currentScope(),
                        rinhaProgram.currentScope()),
                Optional.ofNullable(ctx.formalParameterList())
                        .map(p -> p.ID().stream().map(ParseTree::getText).toList())
                        .orElseGet(List::of),
                ctx.block()
        );
    }

    @Override
    public Value visitTuple(RinhaParser.TupleContext ctx) {
        return Value.getTuple(
                visitTerm(ctx.term(0)),
                visitTerm(ctx.term(1))
        );
    }

    @Override
    public Value visitLiteral(RinhaParser.LiteralContext ctx) {
        return Optional.ofNullable(ctx.INT())
                .map(x -> Value.getInt(x.getText()))
                .or(() -> Optional.ofNullable(ctx.BOOL())
                        .map(x -> Value.getBool(x.getText())))
                .or(() -> Optional.ofNullable(ctx.STRING())
                        .map(x -> Value.getStr(x.getText())))
                .orElseThrow(() -> new RuntimeException("Cannot evaluate literal '" + ctx.getText() + "'."));
    }

    @Override
    public Value visitId(RinhaParser.IdContext ctx) {
        return rinhaProgram.resolve(ctx.getText());
    }

    @Override
    public Value visitIf(RinhaParser.IfContext ctx) {
        Value ifClause = visitTerm(ctx.term());

        if (ifClause instanceof Bool ifClauseBool) {

            if (ifClauseBool.v()) {
                return visitTerm(ctx.then().term());
            } else {
                return visitTerm(ctx.otherwise().term());
            }
        } else {
            throw new RuntimeException("'" + ctx.getText() + " is not a boolean expression");
        }
    }

    @Override
    public Value visitPrint(RinhaParser.PrintContext ctx) {
        Value expressionValue = visitTerm(ctx.term());
        rinhaProgram.println(expressionValue);
        return expressionValue;
    }

    @Override
    public Value visitFirst(RinhaParser.FirstContext ctx) {
        Value value = visitTerm(ctx.term());
        if (value instanceof Tuple tuple) {
            return tuple.left();
        } else {
            throw new RuntimeException("'" + value.getClass().getSimpleName() + "' cannot be converted to 'Tuple'.");
        }
    }

    @Override
    public Value visitSecond(RinhaParser.SecondContext ctx) {
        Value value = visitTerm(ctx.term());
        if (value instanceof Tuple tuple) {
            return tuple.right();
        } else {
            throw new RuntimeException("'" + value.getClass().getSimpleName() + "' cannot be converted to 'Tuple'.");
        }
    }
}
