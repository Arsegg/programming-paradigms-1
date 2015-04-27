package expression.generic;

public class GenericDouble extends GenericNumber<Double, GenericDouble> {
    public GenericDouble(Double first) {
        super(first);
    }

    public GenericDouble(String first) {
        super(Double.parseDouble(first));
    }

    public GenericDouble(Integer first) {
        super(first.doubleValue());
    }

    @Override
    protected Double applyNegate(Double first) {
        return -first;
    }

    @Override
    protected Double applyAbs(Double first) {
        return first >= 0. ? first : -first;
    }

    @Override
    protected Double applySquare(Double first) {
        return first * first;
    }

    @Override
    protected Double applyAdd(Double first, Double second) {
        return first + second;
    }

    @Override
    protected Double applySubtract(Double first, Double second) {
        return first - second;
    }

    @Override
    protected Double applyMultiply(Double first, Double second) {
        return first * second;
    }

    @Override
    protected Double applyDivide(Double first, Double second) {
        return first / second;
    }

    @Override
    protected Double applyModulo(Double first, Double second) {
        return first % second;
    }

    @Override
    protected int applyCompareTo(Double first, Double second) {
        return first > second ? 1 : first < second ? -1 : 0;
    }

    @Override
    protected GenericDouble create(Double first) {
        return new GenericDouble(first);
    }
}
