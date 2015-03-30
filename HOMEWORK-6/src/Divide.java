public class Divide extends BinaryOperator implements TripleExpression {

    public Divide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int apply(int first, int second) {
        return first / second;
    }
}
