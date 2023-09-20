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
    public String toString() {
        return String.valueOf(v);
    }
}
