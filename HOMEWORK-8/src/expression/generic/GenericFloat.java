package expression.generic;

public class GenericFloat extends GenericNumber<Float, GenericFloat> {
    public GenericFloat(Float first) {
        super(first);
    }

    public GenericFloat(String first) {
        super(Float.parseFloat(first));
    }

    public GenericFloat(Integer first) {
        super(first.floatValue());
    }

    @Override
    protected Float applyNegate(Float first) {
        return -first;
    }

    @Override
    protected Float applyAbs(Float first) {
        return first >= 0. ? first : -first;
    }

    @Override
    protected Float applySquare(Float first) {
        return first * first;
    }

    @Override
    protected Float applyAdd(Float first, Float second) {
        return first + second;
    }

    @Override
    protected Float applySubtract(Float first, Float second) {
        return first - second;
    }

    @Override
    protected Float applyMultiply(Float first, Float second) {
        return first * second;
    }

    @Override
    protected Float applyDivide(Float first, Float second) {
        return first / second;
    }

    @Override
    protected Float applyModulo(Float first, Float second) {
        return first % second;
    }

    @Override
    protected int applyCompareTo(Float first, Float second) {
        return first > second ? 1 : first < second ? -1 : 0;
    }

    @Override
    protected GenericFloat create(Float first) {
        return new GenericFloat(first);
    }
}
