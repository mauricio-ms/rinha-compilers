import antlr.RinhaBaseListener;
import antlr.RinhaParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RinhaToJava extends RinhaBaseListener {

    private final Map<String, Value> variables = new HashMap<>();
    private final Map<String, Function> functions = new HashMap<>();

    @Override
    public void enterVariableDeclaration(RinhaParser.VariableDeclarationContext ctx) {
        variables.put(ctx.assignable().getText(), evaluateSingleExpression(ctx.singleExpression()));
    }

    @Override
    public void enterFunctionDeclaration(RinhaParser.FunctionDeclarationContext ctx) {
        functions.put(
                ctx.ID().getText(),
                new Function(ctx.formalParameterList().ID().stream().map(ParseTree::getText).toList(), ctx.statement())
        );
    }

    @Override
    public void enterPrint(RinhaParser.PrintContext ctx) {
        System.out.println(evaluateSingleExpression(ctx.singleExpression()).value());
    }

    private Value evaluateSingleExpression(RinhaParser.SingleExpressionContext ctx) {
        return Optional.ofNullable(ctx.functionCall())
                .map(this::evaluateFunction)
                .or(() -> Optional.ofNullable(ctx.ifStatement())
                        .map(this::evaluateIfStatement))
                .or(() -> Optional.ofNullable(ctx.literal())
                        .map(this::evaluateLiteral))
                .or(() -> Optional.ofNullable(ctx.ID())
                        .map(this::evaluateVariable))
                .orElseThrow(() -> new RuntimeException("Expression " + ctx.getText() + " cannot be evaluated."));
    }

    private Value evaluateFunction(RinhaParser.FunctionCallContext ctx) {
        // TODO - Throws exception if function doesn't exists
        Function function = functions.get(ctx.ID().getText());
        var statements = function.statements();
        if (statements.isEmpty()) {
            return null;
        }

        List<String> parameters = function.parameters();
        var expressions = ctx.singleExpressionList().singleExpression();
        for (int i = 0; i < parameters.size(); i++) {
            // TODO - Implement shadowing
            variables.put(parameters.get(i), evaluateSingleExpression(expressions.get(i)));
        }

        // TODO - will break with multiple expressions
        return evaluateStatement(statements.get(statements.size() - 1));
    }

    private Value evaluateStatement(RinhaParser.StatementContext ctx) {
        var singleExpression = ctx.singleExpression();
        if (singleExpression != null) {
            return evaluateSingleExpression(singleExpression);
        }
        return null;
    }

    // TODO - else if can exists?
    private Value evaluateIfStatement(RinhaParser.IfStatementContext ctx) {
        boolean ifClause = evaluateBooleanExpression(ctx.singleExpression());

        // TODO - will break with multiple expressions
        int blockStatementsIndex = ifClause ? 0 : ctx.ELSE() != null ? 1 : -1;
        if (blockStatementsIndex == -1) {
            throw new RuntimeException("if statement malformed '" + ctx.getText() + "'.");
        }

        var ifBlockStatements = ctx.block(blockStatementsIndex).statement();
        return evaluateStatement(ifBlockStatements.get(ifBlockStatements.size() - 1));
    }

    private boolean evaluateBooleanExpression(RinhaParser.SingleExpressionContext ctx) {
        return Optional.ofNullable(evaluateSingleExpression(ctx))
                .filter(v -> v.type() == Value.Type.BOOL)
                .map(v -> (boolean) v.value())
                .orElseThrow(() -> new RuntimeException("'" + ctx.getText() + " is not a boolean expression"));
    }

    private Value evaluateLiteral(RinhaParser.LiteralContext ctx) {
        return Optional.ofNullable(ctx.INT())
                .map(x -> new Value(Value.Type.INT, x.getText()))
                .or(() -> Optional.ofNullable(ctx.BOOL())
                        .map(x -> new Value(Value.Type.BOOL, Boolean.parseBoolean(x.getText()))))
                .or(() -> Optional.ofNullable(ctx.STRING())
                        .map(x -> new Value(Value.Type.STRING, x.getText())))
                .orElseThrow(() -> new RuntimeException("Cannot evaluate literal '" + ctx.getText() + "'."));
    }

    private Value evaluateVariable(TerminalNode terminalNode) {
        return Optional.ofNullable(variables.get(terminalNode.getText()))
                .orElseThrow(() -> new RuntimeException("Variable '" + terminalNode.getText() + "' not declared."));
    }
}
