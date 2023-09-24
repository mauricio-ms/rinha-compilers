import antlr.RinhaParser;

import java.util.List;
import java.util.Optional;

public class Function extends Value {
    private static final String CLOSURE = "<#closure>";

    private final Scope closureScope;
    private final String name;
    private final List<String> parameters;
    private final RinhaParser.BlockContext block;

    public Function(Scope closureScope, String name, List<String> parameters, RinhaParser.BlockContext block) {
        this.closureScope = closureScope;
        this.name = Optional.ofNullable(name).orElse(CLOSURE);
        this.parameters = parameters;
        this.block = block;
    }

    public Scope closureScope() {
        return closureScope;
    }

    public String name() {
        return name;
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
