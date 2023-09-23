import antlr.RinhaParser;

import java.util.List;
import java.util.Optional;

public class Function extends Value {
    private static final String CLOSURE = "<#closure>";

    private final Scope scope;
    private final String name;
    private final List<String> parameters;
    private final RinhaParser.BlockContext block;

    public Function(Scope enclosingScope, String name, List<String> parameters, RinhaParser.BlockContext block) {
        scope = new FunctionScope(enclosingScope);
        this.name = Optional.ofNullable(name).orElse(CLOSURE);
        this.parameters = parameters;
        this.block = block;
    }

    public Scope scope() {
        return scope;
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
