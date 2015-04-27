package expression.generic;

public class Variable<T extends GenericNumber> implements TripleExpression<T> {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        if (name.equals("x")) {
            return x;
        } else if (name.equals("y")) {
            return y;
        } else if (name.equals("z")) {
            return z;
        }
        return null;
    }
}
