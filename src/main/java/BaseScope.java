import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

abstract class BaseScope implements Scope {
    private final Map<String, Value> variables;
    private final Map<String, Function> functions;
    private final Scope enclosingScope;

    public BaseScope(Scope enclosingScope) {
        variables = new HashMap<>();
        functions = new HashMap<>();
        this.enclosingScope = enclosingScope;
    }

    @Override
    public Scope enclosing() {
        return enclosingScope;
    }

    @Override
    public void declare(String name, Value value) {
        variables.put(name, value);
    }

    @Override
    public Value resolveVariable(String name) {
        return Optional.ofNullable(variables.get(name))
                .or(() -> Optional.ofNullable(enclosingScope)
                        .map(s -> s.resolveVariable(name)))
                .orElse(null);
    }

    @Override
    public void declare(String name, Function function) {
        functions.put(name, function);
    }

    @Override
    public Function resolveFunction(String name) {
        return Optional.ofNullable(functions.get(name))
                .or(() -> Optional.ofNullable(enclosingScope)
                        .map(s -> s.resolveFunction(name)))
                .orElse(null);
    }

    @Override
    public String toString() {
        return name() + ":" + variables + ":" + functions;
    }
}
