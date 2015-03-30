public class Add extends BinaryOperator implements TripleExpression {

    public Add(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int apply(int first, int second) {
        return first + second;
    }

}
