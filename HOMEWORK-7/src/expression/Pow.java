package expression;

public class Pow extends BinaryOperator {
    public Pow(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected String name() {
        return "Power";
    }

    @Override
    protected int apply(int first, int second) {
        if (second == 0 && first == 0 || second < 0) {
            throw new ArithmeticException("undefined");
        }
        int ans = 1;
        while (second > 0) {
            if (second % 2 == 0) {
                if (first > 0 ? first > Integer.MAX_VALUE / first
                    || first < Integer.MIN_VALUE / first
                    : (first < -1 ? first > Integer.MIN_VALUE / first
                    || first < Integer.MAX_VALUE / first
                    : first == -1
                    && first == Integer.MIN_VALUE)) {
                throw new ArithmeticException("overflow");
            }
                first *= first;
                second /= 2;
            } else {
                if (first > 0 ? ans > Integer.MAX_VALUE / first
                        || ans < Integer.MIN_VALUE / first
                        : (first < -1 ? ans > Integer.MIN_VALUE / first
                        || ans < Integer.MAX_VALUE / first
                        : first == -1
                        && ans == Integer.MIN_VALUE)) {
                    throw new ArithmeticException("overflow");
                }
                ans *= first;
                second--;
            }
        }
        return ans;
    }
}
