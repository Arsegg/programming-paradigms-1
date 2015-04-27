package expression.generic;

public class Negate<T extends GenericNumber> extends UnaryOperator<T> {
    public Negate(TripleExpression<T> first) {
        super(first);
    }

    @Override
    protected T apply(T first) {
        return (T) first.negate();
    }
}
