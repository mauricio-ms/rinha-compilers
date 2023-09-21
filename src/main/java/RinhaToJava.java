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
    public Value visitSingleExpression(RinhaParser.SingleExpressionContext ctx) {
        return Optional.ofNullable(ctx.bop)
                .map(bop -> evaluateBinOp(bop.getText(), ctx.singleExpression(0), ctx.singleExpression(1)))
                .orElseGet(() -> visitChildren(ctx));
    }

    private Value evaluateBinOp(String bop, RinhaParser.SingleExpressionContext leftExpr, RinhaParser.SingleExpressionContext rightExpr) {
        Value leftValue = visitSingleExpression(leftExpr);
        Value rightValue = visitSingleExpression(rightExpr);
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

    @Override
    public Value visitBlock(RinhaParser.BlockContext ctx) {
        Value value = null;
        for (var statement : ctx.statement()) {
            value = visitStatement(statement);
        }
        return value;
    }

    // TODO - Rule name ID?
    // TODO - Reserved words?
    @Override
    public Value visitVariableDeclaration(RinhaParser.VariableDeclarationContext ctx) {
        rinhaProgram.declareVariable(ctx.assignable().getText(), visitSingleExpression(ctx.singleExpression()));
        return visitChildren(ctx);
    }

    @Override
    public Value visitFunctionDeclaration(RinhaParser.FunctionDeclarationContext ctx) {
        rinhaProgram.declareFunction(
                ctx.ID().getText(),
                new Function(
                        ctx.formalParameterList().ID().stream().map(ParseTree::getText).toList(),
                        ctx.block()
                )
        );
        return null;
    }

    @Override
    public Value visitPrint(RinhaParser.PrintContext ctx) {
        Value expressionValue = visitSingleExpression(ctx.singleExpression());
        rinhaProgram.println(expressionValue);
        return expressionValue;
    }

    @Override
    public Value visitFunctionCall(RinhaParser.FunctionCallContext ctx) {
        String functionName = ctx.ID().getText();
        Function function = rinhaProgram.loadFunction(functionName);

        List<String> parameters = function.parameters();
        var expressions = ctx.singleExpressionList().singleExpression();
        if (parameters.size() != expressions.size()) {
            String prefixMessage = parameters.isEmpty() ? "No parameter expected" :
                    parameters.size() == 1 ? "Expected 1 parameter" :
                            "Expected " + parameters.size() + " parameters";
            throw new RuntimeException(prefixMessage + " for function '" + ctx.ID().getText() + "' but found " + expressions.size());
        }

        rinhaProgram.setCurrentScope(new FunctionScope(rinhaProgram.currentScope(), functionName));
        for (int i = 0; i < parameters.size(); i++) {
            rinhaProgram.declareVariable(parameters.get(i), visitSingleExpression(expressions.get(i)));
        }

        Value blockReturn = visitBlock(function.block());
        rinhaProgram.deleteCurrentScope();
        return blockReturn;
    }

    // TODO - else if can exists?
    @Override
    public Value visitIfStatement(RinhaParser.IfStatementContext ctx) {
        Value ifClause = visitSingleExpression(ctx.singleExpression());

        if (ifClause instanceof Bool ifClauseBool) {
            int blockStatementsIndex = ifClauseBool.v() ? 0 : ctx.ELSE() != null ? 1 : -1;
            if (blockStatementsIndex == -1) {
                throw new RuntimeException("if statement malformed '" + ctx.getText() + "'.");
            }

            var block = ctx.block(blockStatementsIndex);
            return visitBlock(block);
        } else {
            throw new RuntimeException("'" + ctx.getText() + " is not a boolean expression");
        }
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
        return rinhaProgram.readVariable(ctx.getText());
    }
}
