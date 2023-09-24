import java.util.Optional;

public class FunctionScope extends BaseScope {
    private final Scope closureScope;

    public FunctionScope(Scope closureScope, Scope enclosingScope) {
        super(enclosingScope);
        this.closureScope = closureScope;
    }

    @Override
    public Value resolve(String name) {
        return Optional.ofNullable(closureScope.get(name))
                        .or(() -> Optional.ofNullable(get(name)))
                                .or(() -> Optional.ofNullable(closureScope.resolve(name)))
                .orElseGet(() -> super.resolve(name));
    }

    @Override
    public String name() {
        return "function";
    }
}
