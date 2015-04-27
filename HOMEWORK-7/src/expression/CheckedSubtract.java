package expression;

public class CheckedSubtract extends BinaryOperator implements TripleExpression {
    @Override
    protected String name() {
        return "CheckedSubtract";
    }

    public CheckedSubtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int apply(int first, int second) {
        if (second >= 0 && first < Integer.MIN_VALUE + second || second < 0 && Integer.MAX_VALUE + second < first) {
            throw new ArithmeticException("overflow");
        }
        return first - second;
    }
}
