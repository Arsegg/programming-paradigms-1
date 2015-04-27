package expression.generic;

public class Const<T extends GenericNumber> implements TripleExpression<T> {
    private T value;

    public Const(T value) {
        this.value = value;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return value;
    }
}
