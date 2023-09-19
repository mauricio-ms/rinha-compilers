public record Value(
        Type type,
        Object value
) {
    enum Type {
        INT,
        BOOL,
        STRING
    }
}
