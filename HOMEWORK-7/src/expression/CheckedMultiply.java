package expression;

public class CheckedMultiply extends BinaryOperator implements TripleExpression {
    @Override
    protected String name() {
        return "CheckedMultiply";
    }

    public CheckedMultiply(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int apply(int first, int second) {
        if (second > 0 ? first > Integer.MAX_VALUE / second
                || first < Integer.MIN_VALUE / second
                : (second < -1 ? first > Integer.MIN_VALUE / second
                || first < Integer.MAX_VALUE / second
                : second == -1
                && first == Integer.MIN_VALUE)) {
            throw new ArithmeticException("overflow");
        }
        return first * second;
    }
}
