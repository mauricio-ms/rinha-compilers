public interface Scope {
    String name();
    Scope enclosing();
    void declare(String name, Value value);
    Value resolveVariable(String name);
    void declare(String name, Function function);
    Function resolveFunction(String name);
}
