class GlobalScope extends BaseScope implements Scope {
    public GlobalScope() {
        super(null);
    }

    @Override
    public String name() {
        return "global";
    }
}
