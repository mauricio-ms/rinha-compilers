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

    public Function loadFunction(String name) {
        Value value = resolve(name);
        if (value instanceof Function function) {
            return function;
        } else {
            throw new RuntimeException("Symbol '" + name + "' is not a function.");
        }
    }

    public void println(Value value) {
        System.out.println(value);
    }
}
