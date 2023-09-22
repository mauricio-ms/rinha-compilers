class Tuple extends Value {
    private final Value left;
    private final Value right;

    public Tuple(Value left, Value right) {
        this.left = left;
        this.right = right;
    }

    public Value left() {
        return left;
    }

    public Value right() {
        return right;
    }

    @Override
    public String toString() {
        return "(%s, %s)".formatted(left, right);
    }
}
