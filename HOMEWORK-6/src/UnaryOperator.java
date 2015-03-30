public abstract class UnaryOperator implements TripleExpression {
    private TripleExpression first;

    public UnaryOperator(TripleExpression first) {
        this.first = first;
    }

    protected abstract int apply(int first);

    @Override
    public int evaluate(int x, int y, int z) {
        return apply(first.evaluate(x, y, z));
    }
}
