public abstract class BinaryOperator implements TripleExpression {
    protected TripleExpression first;
    protected TripleExpression second;

    public BinaryOperator(TripleExpression first, TripleExpression second) {
        this.first = first;
        this.second = second;
    }

    protected abstract int apply(int first, int second);

    @Override
    public int evaluate(int x, int y, int z) {
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
