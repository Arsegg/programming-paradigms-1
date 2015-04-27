package expression.generic;

public class Abs<T extends GenericNumber> extends UnaryOperator<T> {
    public Abs(TripleExpression<T> first) {
        super(first);
    }

    @Override
    protected T apply(T first) {
        return (T) first.abs();
    }
}
