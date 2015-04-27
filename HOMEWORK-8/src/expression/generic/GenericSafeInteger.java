package expression.generic;

public class GenericSafeInteger extends GenericNumber<Integer, GenericSafeInteger> {
    public GenericSafeInteger(Integer first) {
        super(first);
    }

    public GenericSafeInteger(String first) {
        super(Integer.parseUnsignedInt(first));
    }

    @Override
    protected Integer applyNegate(Integer first) {
        return -first;
    }

    @Override
    protected Integer applyAbs(Integer first) {
        return first >= 0 ? first : -first;
    }

    @Override
    protected Integer applySquare(Integer first) {
        return applyMultiply(first, first);
    }

    @Override
    protected Integer applyAdd(Integer first, Integer second) {
        if (second >= 0 && Integer.MAX_VALUE - second < first || second < 0 && first < Integer.MIN_VALUE - second) {
            throw new ArithmeticException("overflow");
        }
        return first + second;
    }

    @Override
    protected Integer applySubtract(Integer first, Integer second) {
        if (second >= 0 && first < Integer.MIN_VALUE + second || second < 0 && Integer.MAX_VALUE + second < first) {
            throw new ArithmeticException("overflow");
        }
        return first - second;
    }

    @Override
    protected Integer applyMultiply(Integer first, Integer second) {
        if (second > 0 ? first > Integer.MAX_VALUE / second
                || first < Integer.MIN_VALUE / second
                : (second < -1 ? first > Integer.MIN_VALUE / second
                || first < Integer.MAX_VALUE / second
                : second == -1
                && first == Integer.MIN_VALUE)) {
            throw new ArithmeticException("overflow");
        }
        return first * second;
    }

    @Override
    protected Integer applyDivide(Integer first, Integer second) {
        if ((first == Integer.MIN_VALUE) && (second == -1)) {
            throw new ArithmeticException("overflow");
        }
        if (second == 0) {
            throw new ArithmeticException("division by zero");
        }
        return first / second;
    }

    @Override
    protected Integer applyModulo(Integer first, Integer second) {
        if ((first == Integer.MIN_VALUE) && (second == -1)) {
            throw new ArithmeticException("overflow");
        }
        if (second == 0) {
            throw new ArithmeticException("division by zero");
        }
        return first % second;
    }

    @Override
    protected int applyCompareTo(Integer first, Integer second) {
        return first > second ? 1 : first < second ? -1 : 0;
    }

    @Override
    protected GenericSafeInteger create(Integer first) {
        return new GenericSafeInteger(first);
    }
}
