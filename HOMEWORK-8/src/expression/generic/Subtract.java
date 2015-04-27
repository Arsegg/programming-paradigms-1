package expression.generic;

public class Subtract<T extends GenericNumber> extends BinaryOperator<T> {
    public Subtract(TripleExpression<T> first, TripleExpression<T> second) {
        super(first, second);
    }

    @Override
    protected T apply(T first, T second) {
        return (T) first.subtract(second);
    }
}
