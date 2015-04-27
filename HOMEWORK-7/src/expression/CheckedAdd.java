package expression;

public class CheckedAdd extends BinaryOperator implements TripleExpression {
    @Override
    protected String name() {
        return "CheckedAdd";
    }

    public CheckedAdd(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    public int apply(int first, int second) {
        if (second >= 0 && Integer.MAX_VALUE - second < first || second < 0 && first < Integer.MIN_VALUE - second) {
            throw new ArithmeticException("overflow");
        }
        return first + second;
    }
}
