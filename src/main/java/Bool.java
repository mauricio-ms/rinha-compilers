class Bool extends Value {
    private final boolean v;
    Bool(boolean v) {
        this.v = v;
    }

    public boolean v() {
        return v;
    }

    // TODO - Bool cannot add or be added to any type?
    @Override
    public Value add(Int other) {
        throw new RuntimeException("Operator '+' cannot be applied to 'Bool', 'Int'.");
    }

    @Override
    public Value add(Bool other) {
        throw new RuntimeException("Operator '+' cannot be applied to 'Bool', 'Bool'.");
    }

    @Override
    public Value add(Str other) {
        throw new RuntimeException("Operator '+' cannot be applied to 'Bool', 'Str'.");
    }

    @Override
    public String toString() {
        return String.valueOf(v);
    }
}
