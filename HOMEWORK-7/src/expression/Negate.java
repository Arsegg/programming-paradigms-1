package expression;

public class Negate extends UnaryOperator {
    public Negate(TripleExpression first) {
        super(first);
    }

    @Override
    protected int apply(int first) {
        return -first;
    }
}
