import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

abstract class BaseScope implements Scope {
    private final Map<String, Value> symbols;
    private final Scope enclosingScope;

    public BaseScope(Scope enclosingScope) {
        symbols = new HashMap<>();
        this.enclosingScope = enclosingScope;
    }

    @Override
    public Scope enclosing() {
        return enclosingScope;
    }

    @Override
    public void declare(String name, Value value) {
        symbols.put(name, value);
    }

    @Override
    public Value resolve(String name) {
        return Optional.ofNullable(symbols.get(name))
                .or(() -> Optional.ofNullable(enclosingScope)
                        .map(s -> s.resolve(name)))
                .orElse(null);
    }

    @Override
    public String toString() {
        return name() + ":" + symbols;
    }
}
