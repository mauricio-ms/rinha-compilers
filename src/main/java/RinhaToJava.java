import antlr.RinhaBaseListener;
import antlr.RinhaParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;

public class RinhaToJava extends RinhaBaseListener {

    private final Map<String, String> variables = new HashMap<>();
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
        System.out.println(evaluateSingleExpression(ctx.singleExpression()));
    }

    private String evaluateSingleExpression(RinhaParser.SingleExpressionContext ctx) {
        return Optional.ofNullable(ctx.functionCall())
                .map(this::evaluateFunction)
                .or(() -> Optional.ofNullable(ctx.literal())
                        .map(ParseTree::getText))
                .or(() -> Optional.ofNullable(ctx.ID())
                        .map(ParseTree::getText)
                        .map(this::readVariable)
                        .or(() -> Optional.ofNullable(ctx.STRING())
                                .map(ParseTree::getText)))
                .orElseThrow(() -> new RuntimeException("Expression " + ctx.getText() + " cannot be evaluated."));
    }

    private String readVariable(String name) {
        return Optional.ofNullable(variables.get(name))
                .orElseThrow(() -> new RuntimeException("Variable '" + name + "' not declared."));
    }

    private String evaluateFunction(RinhaParser.FunctionCallContext ctx) {
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

        return evaluateStatement(statements.get(statements.size() - 1));
    }

    private String evaluateStatement(RinhaParser.StatementContext ctx) {
        var singleExpression = ctx.singleExpression();
        if (singleExpression != null) {
            return evaluateSingleExpression(singleExpression);
        }
        return null;
    }
}
