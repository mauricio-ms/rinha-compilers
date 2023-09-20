class Str extends Value {
    private final String v;
    Str(String v) {
        this.v = v;
    }

    public String v() {
        return v;
    }

    @Override
    public Value add(Int other) {
        return new Str(v + other.v());
    }

    // TODO - Int + Bool not allowed?
    @Override
    public Value add(Bool other) {
        throw new RuntimeException("Operator '+' cannot be applied to 'Str', 'Bool'.");
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
