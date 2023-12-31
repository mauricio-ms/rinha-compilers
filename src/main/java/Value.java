// TODO - Improve memory usage by not recreating types in the bin op's
public abstract class Value {

    static Value getInt(String v) {
        return new Int(Integer.parseInt(v));
    }

    static Value getBool(String v) {
        return new Bool(Boolean.parseBoolean(v));
    }

    static Value getStr(String v) {
        return new Str(
                (v == null || v.charAt(0) != '"') ?
                        v : v.substring(1, v.length() - 1)
        );
    }

    static Value getTuple(Value left, Value right) {
        return new Tuple(left, right);
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

    public final Value lt(Value other) {
        if (other instanceof Int i) {
            return lt(i);
        } else if (other instanceof Bool b) {
            return lt(b);
        } else if (other instanceof Str s) {
            return lt(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '<' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value lt(Int other) {
        throw new RuntimeException("Operator '<' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value lt(Bool other) {
        throw new RuntimeException("Operator '<' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value lt(Str other) {
        throw new RuntimeException("Operator '<' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }

    public final Value lte(Value other) {
        if (other instanceof Int i) {
            return lte(i);
        } else if (other instanceof Bool b) {
            return lte(b);
        } else if (other instanceof Str s) {
            return lte(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '<=' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value lte(Int other) {
        throw new RuntimeException("Operator '<=' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value lte(Bool other) {
        throw new RuntimeException("Operator '<=' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value lte(Str other) {
        throw new RuntimeException("Operator '<=' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }

    public final Value gt(Value other) {
        if (other instanceof Int i) {
            return gt(i);
        } else if (other instanceof Bool b) {
            return gt(b);
        } else if (other instanceof Str s) {
            return gt(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '>' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value gt(Int other) {
        throw new RuntimeException("Operator '>' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value gt(Bool other) {
        throw new RuntimeException("Operator '>' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value gt(Str other) {
        throw new RuntimeException("Operator '>' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }

    public final Value gte(Value other) {
        if (other instanceof Int i) {
            return gte(i);
        } else if (other instanceof Bool b) {
            return gte(b);
        } else if (other instanceof Str s) {
            return gte(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '>=' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value gte(Int other) {
        throw new RuntimeException("Operator '>=' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value gte(Bool other) {
        throw new RuntimeException("Operator '>=' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value gte(Str other) {
        throw new RuntimeException("Operator '>=' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }

    public final Value eq(Value other) {
        if (other instanceof Int i) {
            return eq(i);
        } else if (other instanceof Bool b) {
            return eq(b);
        } else if (other instanceof Str s) {
            return eq(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '==' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value eq(Int other) {
        throw new RuntimeException("Operator '==' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value eq(Bool other) {
        throw new RuntimeException("Operator '==' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value eq(Str other) {
        throw new RuntimeException("Operator '==' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }

    public final Value neq(Value other) {
        if (other instanceof Int i) {
            return neq(i);
        } else if (other instanceof Bool b) {
            return neq(b);
        } else if (other instanceof Str s) {
            return neq(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '!=' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value neq(Int other) {
        throw new RuntimeException("Operator '!=' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value neq(Bool other) {
        throw new RuntimeException("Operator '!=' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value neq(Str other) {
        throw new RuntimeException("Operator '!=' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }

    public final Value and(Value other) {
        if (other instanceof Int i) {
            return and(i);
        } else if (other instanceof Bool b) {
            return and(b);
        } else if (other instanceof Str s) {
            return and(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '&&' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value and(Int other) {
        throw new RuntimeException("Operator '&&' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value and(Bool other) {
        throw new RuntimeException("Operator '&&' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value and(Str other) {
        throw new RuntimeException("Operator '&&' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }

    public final Value or(Value other) {
        if (other instanceof Int i) {
            return or(i);
        } else if (other instanceof Bool b) {
            return or(b);
        } else if (other instanceof Str s) {
            return or(s);
        } else {
            throw new RuntimeException("Undefined handling of operation '||' for type '" + other.getClass().getSimpleName() + "', update this code.");
        }
    }

    Value or(Int other) {
        throw new RuntimeException("Operator '||' cannot be applied to '%s', 'Int'."
                .formatted(getClass().getSimpleName()));
    }

    Value or(Bool other) {
        throw new RuntimeException("Operator '||' cannot be applied to '%s', 'Bool'."
                .formatted(getClass().getSimpleName()));
    }

    Value or(Str other) {
        throw new RuntimeException("Operator '||' cannot be applied to '%s', 'Str'."
                .formatted(getClass().getSimpleName()));
    }
}
