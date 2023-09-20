import antlr.RinhaParser;

import java.util.List;

public record Function(
        List<String> parameters,
        RinhaParser.BlockContext block
) {
}
