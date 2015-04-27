package expression.generic;

public abstract class UnaryOperator<T extends GenericNumber> implements TripleExpression<T> {
    private TripleExpression<T> first;

    public UnaryOperator(TripleExpression<T> first) {
        this.first = first;
    }

    protected abstract T apply(T first);

    @Override
    public T evaluate(T x, T y, T z) {
        return apply(first.evaluate(x, y, z));
    }
}
