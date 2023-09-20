import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RinhaProgram {
    private final Map<String, Value> variables = new HashMap<>();
    private final Map<String, Function> functions = new HashMap<>();

    public void declareVariable(String name, Value value) {
        variables.put(name, value);
    }

    public Value readVariable(String name) {
        return Optional.ofNullable(variables.get(name))
                .orElseThrow(() -> new RuntimeException("Variable '" + name + "' not declared."));
    }

    public void declareFunction(String name, Function function) {
        functions.put(name, function);
    }

    public Function loadFunction(String name) {
        // TODO - Throws exception if function is not declared
        return functions.get(name);
    }

    public void println(Object message) {
        System.out.println(message);
    }
}
