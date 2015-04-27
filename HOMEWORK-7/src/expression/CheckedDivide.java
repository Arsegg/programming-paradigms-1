package expression;

public class CheckedDivide extends BinaryOperator implements TripleExpression {
    @Override
    protected String name() {
        return "CheckedDivide";
    }

    public CheckedDivide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int apply(int first, int second) {
        if ((first == Integer.MIN_VALUE) && (second == -1)) {
            throw new ArithmeticException("overflow");
        }
        if (second == 0) {
            throw new ArithmeticException("division by zero");
        }
        return first / second;
    }
}
