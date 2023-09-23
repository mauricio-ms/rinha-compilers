class FunctionScope extends BaseScope {
    public FunctionScope(Scope enclosingScope) {
        super(enclosingScope);
    }

    @Override
    public String name() {
        return "function";
    }
}
