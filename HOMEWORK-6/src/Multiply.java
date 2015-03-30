public class Multiply extends BinaryOperator implements TripleExpression {

    public Multiply(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int apply(int first, int second) {
        return first * second;
    }
}
