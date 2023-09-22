public interface Scope {
    String name();
    Scope enclosing();
    void declare(String name, Value value);
    Value resolve(String name);
}
