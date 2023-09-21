import java.util.Optional;

public class RinhaProgram {
    private Scope currentScope;

    public void declareVariable(String name, Value value) {
        currentScope.declare(name, value);
    }

    public Scope currentScope() {
        return currentScope;
    }

    public void setCurrentScope(Scope currentScope) {
        this.currentScope = currentScope;
    }

    public void deleteCurrentScope() {
        currentScope = currentScope.enclosing();
    }

    public Value readVariable(String name) {
        return Optional.ofNullable(currentScope.resolveVariable(name))
                .orElseThrow(() -> new RuntimeException("Variable '" + name + "' not declared."));
    }

    public void declareFunction(String name, Function function) {
        currentScope.declare(name, function);
    }

    public Function loadFunction(String name) {
        // TODO - Throws exception if function is not declared
        return currentScope.resolveFunction(name);
    }

    public void println(Value value) {
        System.out.println(value);
    }
}
