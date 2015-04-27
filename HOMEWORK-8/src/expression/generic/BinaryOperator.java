package expression.generic;

public abstract class BinaryOperator<T extends GenericNumber> implements TripleExpression<T> {
    private TripleExpression<T> first;
    private TripleExpression<T> second;

    public BinaryOperator(TripleExpression<T> first, TripleExpression<T> second) {
        this.first = first;
        this.second = second;
    }

    protected abstract T apply(T first, T second);

    @Override
    public T evaluate(T x, T y, T z) {
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
