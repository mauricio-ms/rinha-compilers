import java.util.List;

public interface Scope {
    String name();
    Scope enclosing();
    void declare(String name, Value value);
    Value resolve(String name);
    Value get(String name);
    boolean sideEffect();
    void markSideEffect();
    void cache(Function function, List<Value> callParameters, Value result);
    Value readFromCache(Function function, List<Value> callParameters);
}
