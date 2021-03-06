package expression;

public class Const implements TripleExpression {

    private int value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }
}
