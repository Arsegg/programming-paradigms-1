package expression.generic;

public class GenericParser<T> implements Parser<T> {
    private final String mode;
    private String expression;
    private int index;

    private String stringValue;

    private Token currentToken;

    public GenericParser(String mode) {
        this.mode = mode;
    }

    private enum Token {
        NAME,
        NUMBER,
        END,
        PLUS,
        MINUS,
        MUL,
        DIV,
        PRINT,
        ASSIGN,
        LP,
        RP,
        MOD,
        ABS,
        SQUARE,
        LS,
        RS,
        SQRT,
        POW,
        LOG,
    }

    private char get() {
        if (index < expression.length()) {
            return expression.charAt(index);
        }
        return '\0';
    }

    private char pop() {
        char ch = get();
        index++;
        return ch;
    }

    private boolean isDigit(char ch) {
        return Character.isDigit(ch) || ch == '.';
    }

    private Token getToken() {
        char ch = pop();
        while (Character.isWhitespace(ch) && ch != '\0') {
            ch = pop();
        }

        if (ch == '\0') {
            return currentToken = Token.END;
        }
        if (ch == ';' || ch == '\n') {
            return currentToken = Token.PRINT;
        }
        if (isDigit(ch)) {
            StringBuilder number = new StringBuilder();
            number.append(ch);
            while (isDigit(get())) {
                number.append(pop());
            }
            stringValue = number.toString();
            return currentToken = Token.NUMBER;
        }
        if (Character.isAlphabetic(ch)) {
            StringBuilder name = new StringBuilder();
            name.append(ch);
            while (Character.isLetterOrDigit(get())) {
                name.append(pop());
            }
            stringValue = name.toString();
            if (stringValue.equals("mod")) {
                return currentToken = Token.MOD;
            }
            if (stringValue.equals("abs")) {
                return currentToken = Token.ABS;
            }
            if (stringValue.equals("square")) {
                return currentToken = Token.SQUARE;
            }
            if (stringValue.equals("sqrt")) {
                return currentToken = Token.SQRT;
            }
            if (stringValue.equals("x") || stringValue.equals("y") || stringValue.equals("z")) {
                return currentToken = Token.NAME;
            }
            return null;
        }
        if (ch == '*') {
            if (get() == '*') {
                pop();
                return currentToken = Token.POW;
            }
            return currentToken = Token.MUL;
        }
        if (ch == '/') {
            if (get() == '/') {
                pop();
                return currentToken = Token.LOG;
            }
            return currentToken = Token.DIV;
        }
        if (ch == '+') {
            return currentToken = Token.PLUS;
        }
        if (ch == '-') {
            return currentToken = Token.MINUS;
        }
        if (ch == '(') {
            return currentToken = Token.LP;
        }
        if (ch == ')') {
            return currentToken = Token.RP;
        }
        if (ch == '=') {
            return currentToken = Token.ASSIGN;
        }
        if (ch == '<') {
            if (get() == '<') {
                pop();
            }
            return currentToken = Token.LS;
        }
        if (ch == '>') {
            if (get() == '>') {
                pop();
            }
            return currentToken = Token.RS;
        }
        return null;
    }

    private GenericNumber create(String first) {
        if (mode.equals("i")) {
            return new GenericSafeInteger(first);
        }
        if (mode.equals("d")) {
            return new GenericDouble(first);
        }
        if (mode.equals("f")) {
            return new GenericFloat(first);
        }
        if (mode.equals("bi")) {
            return new GenericBigInteger(first);
        }
        if (mode.equals("u") || mode.equals("b")) {
            return new GenericInteger(first);
        }
        return null;
    }

    private TripleExpression prim(boolean get) {
        if (get) {
            getToken();
        }

        if (currentToken == Token.NUMBER) {
            TripleExpression number = new Const(create(stringValue));
            getToken();
            return number;
        }
        if (currentToken == Token.NAME) {
            TripleExpression variable = new Variable(stringValue);
            getToken();
            return variable;
        }
        if (currentToken == Token.MINUS) {
            return new Negate(prim(true));
        }
        if (currentToken == Token.LP) {
            TripleExpression expression = plusMinus(true);
            if (currentToken != Token.RP) {
            }
            getToken();
            return expression;
        }
        if (currentToken == Token.ABS) {
            return new Abs(prim(true));
        }
        if (currentToken == Token.SQUARE) {
            return new Square(prim(true));
        }
        return null;
    }

    private TripleExpression term(boolean get) {
        TripleExpression first = prim(get);
        while (currentToken == Token.MUL || currentToken == Token.DIV || currentToken == Token.MOD) {
            if (currentToken == Token.MUL) {
                first = new Multiply(first, prim(true));
            } else if (currentToken == Token.DIV) {
                first = new Divide(first, prim(true));
            } else {
                first = new Modulo(first, prim(true));
            }
        }
        return first;
    }

    private TripleExpression plusMinus(boolean get) {
        TripleExpression first = term(get);

        while (currentToken == Token.PLUS || currentToken == Token.MINUS) {
            if (currentToken == Token.PLUS) {
                first = new Add(first, term(true));
            } else {
                first = new Subtract(first, term(true));
            }
        }
        return first;
    }

    private void init(String expression) {
        this.expression = expression;
        this.stringValue = null;
        this.currentToken = Token.PRINT;
        this.index = 0;
    }

    @Override
    public TripleExpression<T> parse(String expression) {
        try {
            init(expression);
            getToken();
            return plusMinus(false);
        }
        catch (ArithmeticException e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        Parser parser = new GenericParser<GenericSafeInteger>("i");
        Integer i = Integer.MIN_VALUE;
        TripleExpression expression = parser.parse("-" + i.toString());
        GenericBigInteger zero = new GenericBigInteger(0);
        GenericNumber value = (GenericNumber) expression.evaluate(zero, zero, zero);
        System.out.println(value.value());
    }
}
