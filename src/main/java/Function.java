import antlr.RinhaParser;

import java.util.List;

public record Function(
        List<String> parameters,
        List<RinhaParser.StatementContext> statements
) {
}
