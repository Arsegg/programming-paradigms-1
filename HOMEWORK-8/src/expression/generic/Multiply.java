package expression.generic;

public class Multiply<T extends GenericNumber> extends BinaryOperator<T> {
    public Multiply(TripleExpression<T> first, TripleExpression<T> second) {
        super(first, second);
    }

    @Override
    protected T apply(T first, T second) {
        return (T) first.multiply(second);
    }
}
