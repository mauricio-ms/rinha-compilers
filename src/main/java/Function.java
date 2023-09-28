import antlr.RinhaParser;

import java.util.List;

public class Function extends Value {
    private static final String CLOSURE = "<#closure>";

    private final Scope closureScope;
    private final List<String> parameters;
    private final RinhaParser.BlockContext block;

    public Function(Scope closureScope, List<String> parameters, RinhaParser.BlockContext block) {
        this.closureScope = closureScope;
        this.parameters = parameters;
        this.block = block;
    }

    public Scope closureScope() {
        return closureScope;
    }

    public List<String> parameters() {
        return parameters;
    }

    public RinhaParser.BlockContext block() {
        return block;
    }

    @Override
    public String toString() {
        return CLOSURE;
    }
}
