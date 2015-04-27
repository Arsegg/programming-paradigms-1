package expression;

public class CheckedNegate extends UnaryOperator implements TripleExpression {
    public CheckedNegate(TripleExpression first) {
        super(first);
    }

    @Override
    protected String name() {
        return "CheckedNegate";
    }

    @Override
    protected int apply(int first) {
        if (first == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow");
        }
        return -first;
    }
}
