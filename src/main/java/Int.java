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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Int anInt = (Int) o;

        return v == anInt.v;
    }

    @Override
    public int hashCode() {
        return v;
    }

    @Override
    public String toString() {
        return String.valueOf(v);
    }
}
