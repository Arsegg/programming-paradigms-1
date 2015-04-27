package expression;

public class Abs extends UnaryOperator {
    public Abs(TripleExpression first) {
        super(first);
    }

    @Override
    protected int apply(int first) {
        if (first == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow");
        }
        return first > 0 ? first : -first;
    }
}
