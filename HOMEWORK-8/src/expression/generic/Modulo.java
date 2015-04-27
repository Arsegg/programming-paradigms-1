package expression.generic;

public class Modulo<T extends GenericNumber> extends BinaryOperator<T> {
    public Modulo(TripleExpression<T> first, TripleExpression<T> second) {
        super(first, second);
    }

    @Override
    protected T apply(T first, T second) {
        return (T) first.modulo(second);
    }
}
