import antlr.RinhaBaseListener;
import antlr.RinhaParser;
import org.antlr.runtime.tree.BaseTree;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RinhaToJava extends RinhaBaseListener {

    private final Map<String, String> variables = new HashMap<>();

    @Override
    public void enterVariableDeclaration(RinhaParser.VariableDeclarationContext ctx) {
        variables.put(ctx.assignable().getText(), evaluateSingleExpression(ctx.singleExpression()));
    }

    @Override
    public void enterPrint(RinhaParser.PrintContext ctx) {
        System.out.println(evaluateSingleExpression(ctx.singleExpression()));
    }

    private String evaluateSingleExpression(RinhaParser.SingleExpressionContext ctx) {
        return Optional.<RuleNode>ofNullable(ctx.literal())
                .map(ParseTree::getText)
                .or(() -> Optional.ofNullable(ctx.ID())
                        .map(ParseTree::getText)
                        .map(variables::get)
                        .or(() -> Optional.ofNullable(ctx.STRING())
                                .map(ParseTree::getText)))
                .orElseThrow();
    }
}
