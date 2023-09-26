import java.util.List;
import java.util.Optional;

public class RinhaProgram {
    private Scope currentScope;

    public Scope currentScope() {
        return currentScope;
    }

    public void setCurrentScope(Scope currentScope) {
        this.currentScope = currentScope;
    }

    public void deleteCurrentScope() {
        currentScope = currentScope.enclosing();
    }

    public void declare(String name, Value value) {
        currentScope.declare(name, value);
    }

    public Value resolve(String name) {
        return Optional.ofNullable(currentScope.resolve(name))
                .orElseThrow(() -> new RuntimeException("Symbol '" + name + "' not declared."));
    }

    public void cache(Function function, List<Value> callParameters, Value result) {
        currentScope.cache(function, callParameters, result);
    }

    public Value readFromCache(Function function, List<Value> callParameters) {
        return currentScope.readFromCache(function, callParameters);
    }

    public void println(Value value) {
        System.out.println(value);
        markSideEffect();
    }

    public void markSideEffect() {
        currentScope.markSideEffect();
    }

    public boolean sideEffect() {
        return currentScope.sideEffect();
    }
}
