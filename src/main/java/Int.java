class Int extends Value {
    private final int v;
    Int(int v) {
        this.v = v;
    }

    public int v() {
        return v;
    }

    @Override
    public Value add(Int other) {
        return new Int(v + other.v());
    }

    // TODO - Int + Bool not allowed?
    @Override
    public Value add(Bool other) {
        throw new RuntimeException("Operator '+' cannot be applied to 'Int', 'Bool'.");
    }

    @Override
    public Value add(Str other) {
        return new Str(v + other.v());
    }

    @Override
    public String toString() {
        return String.valueOf(v);
    }
}
