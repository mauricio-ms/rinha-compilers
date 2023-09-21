class Tuple extends Value {
    private final Value left;
    private final Value right;

    public Tuple(Value left, Value right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(%s, %s)".formatted(left, right);
    }
}
