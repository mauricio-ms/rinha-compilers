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
    public Value add(Str other) {
        return new Str(v + other.v());
    }

    @Override
    Value sub(Int other) {
        return new Int(v - other.v());
    }

    @Override
    Value mul(Int other) {
        return new Int(v * other.v());
    }

    @Override
    Value div(Int other) {
        return new Int(v / other.v());
    }

    @Override
    public Value rem(Int other) {
        return new Int(v % other.v());
    }

    @Override
    Value lt(Int other) {
        return new Bool(v < other.v());
    }

    @Override
    Value lte(Int other) {
        return new Bool(v <= other.v());
    }

    @Override
    Value gt(Int other) {
        return new Bool(v > other.v());
    }

    @Override
    Value gte(Int other) {
        return new Bool(v >= other.v());
    }

    @Override
    Value eq(Int other) {
        return new Bool(v == other.v());
    }

    @Override
    Value neq(Int other) {
        return new Bool(v != other.v());
    }

    @Override
    public String toString() {
        return String.valueOf(v);
    }
}
