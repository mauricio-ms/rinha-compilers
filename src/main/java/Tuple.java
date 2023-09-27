import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple tuple = (Tuple) o;

        if (!Objects.equals(left, tuple.left)) return false;
        return Objects.equals(right, tuple.right);
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(%s, %s)".formatted(left, right);
    }
}
