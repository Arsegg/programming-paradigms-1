package expression;

public class Square extends UnaryOperator {
    public Square(TripleExpression first) {
        super(first);
    }

    @Override
    protected int apply(int first) {
        return first * first;
    }
}
