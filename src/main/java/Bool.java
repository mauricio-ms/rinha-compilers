class Bool extends Value {

    private final boolean v;
    Bool(boolean v) {
        this.v = v;
    }

    public boolean v() {
        return v;
    }

    @Override
    Value and(Bool other) {
        return new Bool(v && other.v());
    }

    @Override
    Value or(Bool other) {
        return new Bool(v || other.v());
    }

    @Override
    Value eq(Bool other) {
        return new Bool(v == other.v());
    }

    @Override
    Value neq(Bool other) {
        return new Bool(v != other.v());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bool bool = (Bool) o;

        return v == bool.v;
    }

    @Override
    public int hashCode() {
        return (v ? 1 : 0);
    }

    @Override
    public String toString() {
        return String.valueOf(v);
    }
}
