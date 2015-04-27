package expression.generic;

public class Add<T extends GenericNumber> extends BinaryOperator<T> {
    public Add(TripleExpression<T> first, TripleExpression<T> second) {
        super(first, second);
    }

    @Override
    protected T apply(T first, T second) {
        return (T) first.add(second);
    }
}
