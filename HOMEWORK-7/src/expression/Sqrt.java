package expression;

public class Sqrt extends UnaryOperator {
    public Sqrt(TripleExpression first) {
        super(first);
    }

    @Override
    protected String name() {
        return "Sqrt";
    }

    @Override
    protected int apply(int first) {
        if (first < 0) {
            throw new ArithmeticException("negative arg in sqrt");
        }
        int l = -1;
        int r = first + 1;
        while (l + 1 < r) {
            int m = l + (r - l) / 2;
            int value;
            try {
                value = square(m);
            }
            catch (Exception e) {
                value = Integer.MAX_VALUE;
            }
            if (value > first) {
                r = m;
            } else {
                l = m;
            }
        }
        return r - 1;
    }

    private int square(int first) {
        if (first > 0 ? first > Integer.MAX_VALUE / first
                || first < Integer.MIN_VALUE / first
                : (first < -1 ? first > Integer.MIN_VALUE / first
                || first < Integer.MAX_VALUE / first
                : first == -1
                && first == Integer.MIN_VALUE)) {
            throw new ArithmeticException("overflow");
        }
        return first * first;
    }
}
