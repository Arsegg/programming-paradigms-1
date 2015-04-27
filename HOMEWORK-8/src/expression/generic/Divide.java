package expression.generic;

/**
 * Created by HOME on 19.04.2015.
 */
public class Divide<T extends GenericNumber> extends BinaryOperator<T> {
    public Divide(TripleExpression<T> first, TripleExpression<T> second) {
        super(first, second);
    }

    @Override
    protected T apply(T first, T second) {
        return (T) first.divide(second);
    }
}
