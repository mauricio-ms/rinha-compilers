// TODO - Improve memory usage by not recreating types in the bin op's
public abstract class Value {

    // TODO - Doesn't have parenthesized expressions?

    static Value getInt(String v) {
        return new Int(Integer.parseInt(v));
    }

    static Value getBool(String v) {
        return new Bool(Boolean.parseBoolean(v));
    }

    static Value getStr(String v) {
        return new Str(v);
    }

    public final Value add(Value other) {
        if (other instanceof Int i) {
            return add(i);
        } else if (other instanceof Bool b) {
            return add(b);
        } else if (other instanceof Str s) {
            return add(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '+' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value add(Int other) {
        throw new RuntimeException("Operator '+' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value add(Bool other) {
        throw new RuntimeException("Operator '+' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value add(Str other) {
        throw new RuntimeException("Operator '+' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }

    public final Value sub(Value other) {
        if (other instanceof Int i) {
            return sub(i);
        } else if (other instanceof Bool b) {
            return sub(b);
        } else if (other instanceof Str s) {
            return sub(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '-' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value sub(Int other) {
        throw new RuntimeException("Operator '-' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value sub(Bool other) {
        throw new RuntimeException("Operator '-' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value sub(Str other) {
        throw new RuntimeException("Operator '-' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }

    public final Value mul(Value other) {
        if (other instanceof Int i) {
            return mul(i);
        } else if (other instanceof Bool b) {
            return mul(b);
        } else if (other instanceof Str s) {
            return mul(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '*' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value mul(Int other) {
        throw new RuntimeException("Operator '*' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value mul(Bool other) {
        throw new RuntimeException("Operator '*' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value mul(Str other) {
        throw new RuntimeException("Operator '*' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }

    public final Value div(Value other) {
        if (other instanceof Int i) {
            return div(i);
        } else if (other instanceof Bool b) {
            return div(b);
        } else if (other instanceof Str s) {
            return div(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '/' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value div(Int other) {
        throw new RuntimeException("Operator '/' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value div(Bool other) {
        throw new RuntimeException("Operator '/' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value div(Str other) {
        throw new RuntimeException("Operator '/' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }

    public final Value rem(Value other) {
        if (other instanceof Int i) {
            return rem(i);
        } else if (other instanceof Bool b) {
            return rem(b);
        } else if (other instanceof Str s) {
            return rem(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '%' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value rem(Int other) {
        throw new RuntimeException("Operator '%%' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value rem(Bool other) {
        throw new RuntimeException("Operator '%%' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value rem(Str other) {
        throw new RuntimeException("Operator '%%' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }

    public final Value eq(Value other) {
        return new Bool(this.equals(other));
    }

    public final Value neq(Value other) {
        return new Bool(!this.equals(other));
    }
}
