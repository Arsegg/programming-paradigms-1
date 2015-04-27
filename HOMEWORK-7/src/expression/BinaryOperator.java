package expression;

public abstract class BinaryOperator implements TripleExpression {
    private TripleExpression first;
    private TripleExpression second;

    protected String name() {
        return "BinaryOperator";
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", name(), first, second);
    }


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
