import java.util.*;

abstract class BaseScope implements Scope {
    private boolean sideEffect;
    private final Map<Function, Map<List<Value>, Value>> cache;
    private final Map<String, Value> symbols;
    private final Scope enclosingScope;

    public BaseScope(Scope enclosingScope) {
        cache = new HashMap<>();
        symbols = new HashMap<>();
        this.enclosingScope = enclosingScope;
    }

    @Override
    public Scope enclosing() {
        return enclosingScope;
    }

    @Override
    public void declare(String name, Value value) {
        if (symbols.containsKey(name)) {
            throw new RuntimeException("The symbol '" + name + "' is already declared.");
        }
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
    public Value get(String name) {
        return symbols.get(name);
    }

    @Override
    public boolean sideEffect() {
        return sideEffect;
    }

    @Override
    public void markSideEffect() {
        sideEffect = true;
    }

    @Override
    public void cache(Function function, List<Value> callParameters, Value result) {
        if (!isFunctionDeclared(function) && enclosingScope != null) {
            enclosingScope.cache(function, callParameters, result);
        } else if (!sideEffect) {
            cache.computeIfAbsent(function, f -> new HashMap<>());
            cache.get(function).put(callParameters, result);
        }
    }

    @Override
    public Value readFromCache(Function function, List<Value> callParameters) {
        if (isFunctionDeclared(function)) {
            if (cache.containsKey(function)) {
                return cache.get(function).get(callParameters);
            }
            return null;
        } else if (enclosingScope != null) {
            return enclosingScope.readFromCache(function, callParameters);
        } else {
            return null;
        }
    }

    private boolean isFunctionDeclared(Function function) {
        return symbols.values().stream().anyMatch(v -> v == function);
    }

    @Override
    public String toString() {
        return name() + ":" + symbols;
    }
}
