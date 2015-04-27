package expression.generic;

public class Square<T extends GenericNumber> extends UnaryOperator<T> {
    public Square(TripleExpression<T> first) {
        super(first);
    }

    @Override
    protected T apply(T first) {
        return (T) first.square();
    }
}
