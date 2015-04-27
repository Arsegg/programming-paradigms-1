package expression;

public class Variable implements TripleExpression {

    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (name.equals("x")) {
            return x;
        } else if (name.equals("y")) {
            return y;
        } else if (name.equals("z")) {
            return z;
        }
        throw new AssertionError("Wrong variable");
    }
}
