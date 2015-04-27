package expression.generic;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] ans = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        Parser parser = null;
        if (mode.equals("i")) {
            parser = new GenericParser<GenericSafeInteger>(mode);
        } else if (mode.equals("d") || mode.equals("f")) {
            parser = new GenericParser<GenericDouble>(mode);
        } else if (mode.equals("bi")) {
            parser = new GenericParser<GenericBigInteger>(mode);
        } else if (mode.equals("u") || mode.equals("b")) {
            parser = new GenericParser<GenericInteger>(mode);
        }
        for (int i = 0; i <= x2 - x1; i++) {
            for (int j = 0; j <= y2 - y1; j++) {
                for (int k = 0; k <= z2 - z1; k++) {
                    try {
                        final Integer x = x1 + i;
                        final Integer y = y1 + j;
                        final Integer z = z1 + k;
                        TripleExpression expr = parser.parse(expression);
                        GenericNumber number = null;
                        if (mode.equals("i")) {
                            number = (GenericNumber) expr.evaluate(new GenericSafeInteger(x), new GenericSafeInteger(y), new GenericSafeInteger(z));
                        } else if (mode.equals("d")) {
                            number = (GenericNumber) expr.evaluate(new GenericDouble(x), new GenericDouble(y), new GenericDouble(z));
                        } else if (mode.equals("bi")) {
                            number = (GenericNumber) expr.evaluate(new GenericBigInteger(x), new GenericBigInteger(y), new GenericBigInteger(z));
                        } else if (mode.equals("u") || mode.equals("b")) {
                            number = (GenericInteger) expr.evaluate(new GenericInteger(x), new GenericInteger(y), new GenericInteger(z));
                        } else if (mode.equals("f")) {
                            number = (GenericNumber) expr.evaluate(new GenericFloat(x), new GenericFloat(y), new GenericFloat(z));
                        }
                        if (mode.equals("b")) {
                            ans[i][j][k] = ((Integer) number.value()).byteValue();
                        } else {
                            ans[i][j][k] = number.value();
                        }
                    }
                    catch (Exception e) {
                    }
                }
            }
        }

        return ans;
    }
}
