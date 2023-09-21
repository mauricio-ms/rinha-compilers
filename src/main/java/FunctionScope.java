class FunctionScope extends BaseScope {
    private final String functionName;

    public FunctionScope(Scope enclosingScope, String functionName) {
        super(enclosingScope);
        this.functionName = functionName;
    }

    @Override
    public String name() {
        return "fn " + functionName;
    }
}
