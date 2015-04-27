package expression;

public class Log extends BinaryOperator {
    public Log(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected String name() {
        return "Log";
    }

    @Override
    protected int apply(int first, int second) {
        if (second <= 0 || second == 1 || first <= 0) {
            throw new ArithmeticException("undefined");
        }
        if (first < second) {
            return 0;
        }
        int l = -1;
        int r = first + 1;
        while (l + 1 < r) {
            int m = l + (r - l) / 2;
            int value;
            try {
                value = pow(second, m);
            } catch (Exception e) {
                value = Integer.MAX_VALUE;
            }
            if (value > first) {
                r = m;
            } else {
                l = m;
            }
        }
        //return (int) (Math.log(first) / Math.log(second));
        return r - 1;
    }

    private int pow(int first, int second) {
        if (second == 0 && first == 0) {
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
