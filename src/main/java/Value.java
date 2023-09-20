public abstract class Value {

    static Value getInt(String v) {
        return new Int(Integer.parseInt(v));
    }

    static Value getBool(String v) {
        return new Bool(Boolean.parseBoolean(v));
    }

    static Value getStr(String v) {
        return new Str(v);
    }

    public Value add(Value other) {
        if (other instanceof Int i) {
            return add(i);
        } else if (other instanceof Bool b) {
            return add(b);
        } else if (other instanceof Str s) {
            return add(s);
        } else {
            throw new RuntimeException("Undefined handling for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    abstract Value add(Int other);

    abstract Value add(Bool other);

    abstract Value add(Str other);
}
