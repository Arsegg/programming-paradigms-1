package expression.generic;

import java.math.BigInteger;

public class GenericInteger extends GenericNumber<Integer, GenericInteger> {
    public GenericInteger(String first) {
        super(Integer.parseInt(first));
    }

    public GenericInteger(Integer first) {
        super(first);
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
        return first * first;
    }

    @Override
    protected Integer applyAdd(Integer first, Integer second) {
        return first + second;
    }

    @Override
    protected Integer applySubtract(Integer first, Integer second) {
        return first - second;
    }

    @Override
    protected Integer applyMultiply(Integer first, Integer second) {
        return first * second;
    }

    @Override
    protected Integer applyDivide(Integer first, Integer second) {
        return first / second;
    }

    @Override
    protected Integer applyModulo(Integer first, Integer second) {
        return first % second;
    }

    @Override
    protected int applyCompareTo(Integer first, Integer second) {
        return first > second ? 1 : first < second ? -1 : 0;
    }

    @Override
    protected GenericInteger create(Integer first) {
        return new GenericInteger(first);
    }
}
