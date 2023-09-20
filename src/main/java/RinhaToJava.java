import antlr.RinhaBaseVisitor;
import antlr.RinhaParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.Optional;

public class RinhaToJava extends RinhaBaseVisitor<Value> {

    private final RinhaProgram rinhaProgram = new RinhaProgram();

    @Override
    public Value visitBlock(RinhaParser.BlockContext ctx) {
        Value value = null;
        for (var statement : ctx.statement()) {
            value = visitStatement(statement);
        }
        return value;
    }

    @Override
    public Value visitVariableDeclaration(RinhaParser.VariableDeclarationContext ctx) {
        rinhaProgram.declareVariable(ctx.assignable().getText(), visitSingleExpression(ctx.singleExpression()));
        return visitChildren(ctx);
    }

    @Override
    public Value visitFunctionDeclaration(RinhaParser.FunctionDeclarationContext ctx) {
        rinhaProgram.declareFunction(
                ctx.ID().getText(),
                new Function(ctx.formalParameterList().ID().stream().map(ParseTree::getText).toList(), ctx.block())
        );
        return null;
    }

    @Override
    public Value visitPrint(RinhaParser.PrintContext ctx) {
        Value expressionValue = visitSingleExpression(ctx.singleExpression());
        rinhaProgram.println(expressionValue.value());
        return expressionValue;
    }

    @Override
    public Value visitFunctionCall(RinhaParser.FunctionCallContext ctx) {
        String functionName = ctx.ID().getText();
        Function function = rinhaProgram.loadFunction(functionName);

        List<String> parameters = function.parameters();
        var expressions = ctx.singleExpressionList().singleExpression();
        if (parameters.size() != expressions.size()) {
            String prefixMessage = parameters.size() == 0 ? "No parameter expected" :
                    parameters.size() == 1 ? "Expected 1 parameter" :
                            "Expected " + parameters.size() + " parameters";
            throw new RuntimeException(prefixMessage + " for function '" + ctx.ID().getText() + "' but found " + expressions.size());
        }

        for (int i = 0; i < parameters.size(); i++) {
            // TODO - Implement shadowing
            rinhaProgram.declareVariable(parameters.get(i), visitSingleExpression(expressions.get(i)));
        }

        return visitBlock(function.block());
    }

    // TODO - else if can exists?
    @Override
    public Value visitIfStatement(RinhaParser.IfStatementContext ctx) {
        Value ifClause = visitSingleExpression(ctx.singleExpression());
        if (ifClause.type() != Value.Type.BOOL) {
            throw new RuntimeException("'" + ctx.getText() + " is not a boolean expression");
        }

        int blockStatementsIndex = (boolean) ifClause.value() ? 0 : ctx.ELSE() != null ? 1 : -1;
        if (blockStatementsIndex == -1) {
            throw new RuntimeException("if statement malformed '" + ctx.getText() + "'.");
        }

        var block = ctx.block(blockStatementsIndex);
        return visitBlock(block);
    }

    @Override
    public Value visitLiteral(RinhaParser.LiteralContext ctx) {
        return Optional.ofNullable(ctx.INT())
                .map(x -> new Value(Value.Type.INT, x.getText()))
                .or(() -> Optional.ofNullable(ctx.BOOL())
                        .map(x -> new Value(Value.Type.BOOL, Boolean.parseBoolean(x.getText()))))
                .or(() -> Optional.ofNullable(ctx.STRING())
                        .map(x -> new Value(Value.Type.STRING, x.getText())))
                .orElseThrow(() -> new RuntimeException("Cannot evaluate literal '" + ctx.getText() + "'."));
    }

    @Override
    public Value visitId(RinhaParser.IdContext ctx) {
        return rinhaProgram.readVariable(ctx.getText());
    }
}
