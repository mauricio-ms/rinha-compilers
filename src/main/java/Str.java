import java.util.Objects;

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

    // TODO - 2 + "a" should be "2a", not 2"a", besides that, check the print output
    @Override
    public Value add(Str other) {
        return new Str(v + other.v());
    }

    @Override
    Value eq(Str other) {
        return new Bool(v.equals(other.v()));
    }

    @Override
    Value neq(Str other) {
        return new Bool(!v.equals(other.v()));
    }

    @Override
    public String toString() {
        return String.valueOf(v);
    }
}
