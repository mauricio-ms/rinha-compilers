import antlr.RinhaBaseListener;
import antlr.RinhaParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RinhaToJava extends RinhaBaseListener {

    private final Map<String, String> variables = new HashMap<>();
    private final Map<String, List<RinhaParser.StatementContext>> functions = new HashMap<>();

    @Override
    public void enterVariableDeclaration(RinhaParser.VariableDeclarationContext ctx) {
        variables.put(ctx.assignable().getText(), evaluateSingleExpression(ctx.singleExpression()));
    }

    @Override
    public void enterFunctionDeclaration(RinhaParser.FunctionDeclarationContext ctx) {
        functions.put(
                ctx.ID().getText(),
                ctx.statement()
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
                        .map(variables::get) // TODO - Throws exception if var doesn't exist
                        .or(() -> Optional.ofNullable(ctx.STRING())
                                .map(ParseTree::getText)))
                .orElseThrow(() -> new RuntimeException("Expression " + ctx.getText() + " cannot be evaluated."));
    }

    private String evaluateFunction(RinhaParser.FunctionCallContext ctx) {
        // TODO - Throws exception if function doesn't exists
        List<RinhaParser.StatementContext> statementContexts = functions.get(ctx.ID().getText());
        if (statementContexts.isEmpty()) {
            return null;
        }
        return evaluateStatement(statementContexts.get(statementContexts.size() - 1));
    }

    private String evaluateStatement(RinhaParser.StatementContext ctx) {
        var singleExpression = ctx.singleExpression();
        if (singleExpression != null) {
            return singleExpression.getText();
        }
        return null;
    }
}
