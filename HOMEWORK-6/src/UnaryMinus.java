public class UnaryMinus extends UnaryOperator {
    public UnaryMinus(TripleExpression first) {
        super(first);
    }

    @Override
    protected int apply(int first) {
        return -first;
    }
}
