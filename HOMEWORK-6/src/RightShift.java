/**
 * Created by HOME on 24.03.2015.
 */
public class RightShift extends BinaryOperator {
    public RightShift(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int apply(int first, int second) {
        return first >> second;
    }
}
