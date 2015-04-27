package expression;

public class Modulo extends BinaryOperator {
    public Modulo(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int apply(int first, int second) {
        return first % second;
    }
}
