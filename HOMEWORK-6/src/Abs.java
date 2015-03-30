public class Abs extends UnaryOperator {
    public Abs(TripleExpression first) {
        super(first);
    }

    @Override
    protected int apply(int first) {
        return Math.abs(first);
    }
}
