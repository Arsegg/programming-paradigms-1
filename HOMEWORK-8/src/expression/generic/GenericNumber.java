package expression.generic;

public abstract class GenericNumber<T, S extends GenericNumber<T, S>> {
    private T first;

    public GenericNumber(T first) {
        this.first = first;
    }

    public T value() {
        return first;
    }

    protected abstract T applyNegate(T first);

    protected abstract T applyAbs(T first);

    protected abstract T applySquare(T first);

    protected abstract T applyAdd(T first, T second);

    protected abstract T applySubtract(T first, T second);

    protected abstract T applyMultiply(T first, T second);

    protected abstract T applyDivide(T first, T second);

    protected abstract T applyModulo(T first, T second);

    protected abstract int applyCompareTo(T first, T second);

    protected abstract S create(T first);

    public S negate() {
        return create(applyNegate(first));
    }

    public S abs() {
        return create(applyAbs(first));
    }

    public S square() {
        return create(applySquare(first));
    }

    public S add(S second) {
        return create(applyAdd(first, second.value()));
    }

    public S subtract(S second) {
        return create(applySubtract(first, second.value()));
    }

    public S multiply(S second) {
        return create(applyMultiply(first, second.value()));
    }

    public S divide(S second) {
        return create(applyDivide(first, second.value()));
    }

    public S modulo(S second) {
        return create(applyModulo(first, second.value()));
    }

    public int compareTo(S second) {
        return applyCompareTo(first, second.value());
    }

}
