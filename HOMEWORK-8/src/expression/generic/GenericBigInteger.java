package expression.generic;

import java.math.BigInteger;

public class GenericBigInteger extends GenericNumber<BigInteger, GenericBigInteger> {
    public GenericBigInteger(BigInteger first) {
        super(first);
    }

    public GenericBigInteger(String first) {
        super(new BigInteger(first));
    }

    public GenericBigInteger(Integer first) {
        super(new BigInteger(first.toString()));
    }

    @Override
    protected BigInteger applyNegate(BigInteger first) {
        return first.negate();
    }

    @Override
    protected BigInteger applyAbs(BigInteger first) {
        return first.abs();
    }

    @Override
    protected BigInteger applySquare(BigInteger first) {
        return first.multiply(first);
    }

    @Override
    protected BigInteger applyAdd(BigInteger first, BigInteger second) {
        return first.add(second);
    }

    @Override
    protected BigInteger applySubtract(BigInteger first, BigInteger second) {
        return first.subtract(second);
    }

    @Override
    protected BigInteger applyMultiply(BigInteger first, BigInteger second) {
        return first.multiply(second);
    }

    @Override
    protected BigInteger applyDivide(BigInteger first, BigInteger second) {
        return first.divide(second);
    }

    @Override
    protected BigInteger applyModulo(BigInteger first, BigInteger second) {
        return first.abs().mod(second.abs()).multiply(BigInteger.valueOf(first.signum()));
    }

    @Override
    protected int applyCompareTo(BigInteger first, BigInteger second) {
        return first.compareTo(second);
    }

    @Override
    protected GenericBigInteger create(BigInteger first) {
        return new GenericBigInteger(first);
    }
}
