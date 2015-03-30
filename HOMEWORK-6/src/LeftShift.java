public class LeftShift extends BinaryOperator {
    public LeftShift(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int apply(int first, int second) {
        return first << second;
    }
}
