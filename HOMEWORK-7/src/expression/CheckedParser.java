package expression;

public class CheckedParser implements Parser {
    private String expression;
    private int index;

    private String stringValue;
    private int numberValue;

    private Token currentToken;

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

    private Token getToken() throws ParserException {
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
        if (Character.isDigit(ch) || ch == '.') {
            StringBuilder number = new StringBuilder();
            number.append(ch);
            while (Character.isDigit(get()) || get() == '.') {
                number.append(pop());
            }
            numberValue = Integer.parseUnsignedInt(number.toString());
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
            throw new ParserException("Wrong name");
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
            if (currentToken != Token.NUMBER && currentToken != Token.NAME) {

            }
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
        throw new ParserException(String.format("Wrong token at %s", index));
    }

    private TripleExpression prim(boolean get) throws ParserException {
        if (get) {
            getToken();
        }

        if (currentToken == Token.NUMBER) {
            TripleExpression number = new Const(numberValue);
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
            TripleExpression expression = expr(true);
            if (currentToken != Token.RP) {
                throw new ParserException(String.format("Wrong bracket at %s", index));
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
        if (currentToken == Token.SQRT) {
            return new Sqrt(prim(true));
        }
        throw new ParserException(String.format("Wrong token at %s", index));
    }

    private TripleExpression powLog(boolean get) throws ParserException {
        TripleExpression first = prim(get);
        while (currentToken == Token.POW || currentToken == Token.LOG) {
            if (currentToken == Token.POW) {
                first = new Pow(first, prim(true));
            } else {
                first = new Log(first, prim(true));
            }
        }
        return first;
    }

    private TripleExpression term(boolean get) throws ParserException {
        TripleExpression first = powLog(get);
        while (currentToken == Token.MUL || currentToken == Token.DIV || currentToken == Token.MOD) {
            if (currentToken == Token.MUL) {
                first = new CheckedMultiply(first, powLog(true));
            } else if (currentToken == Token.DIV) {
                first = new CheckedDivide(first, powLog(true));
            } else {
                first = new Modulo(first, prim(true));
            }
        }
        return first;
    }

    private TripleExpression plusMinus(boolean get) throws ParserException {
        TripleExpression first = term(get);

        while (currentToken == Token.PLUS || currentToken == Token.MINUS) {
            if (currentToken == Token.PLUS) {
                first = new CheckedAdd(first, term(true));
            } else {
                first = new CheckedSubtract(first, term(true));
            }
        }
        return first;
    }

    private TripleExpression expr(boolean get) throws ParserException {
        TripleExpression first = plusMinus(get);

        while (currentToken == Token.LS || currentToken == Token.RS) {
            if (currentToken == Token.LS) {
                first = new LeftShift(first, plusMinus(true));
            } else {
                first = new RightShift(first, plusMinus(true));
            }
        }
        return first;
    }

    private void init(String expression) {
        this.expression = expression;
        this.stringValue = null;
        this.numberValue = 0;
        this.currentToken = Token.PRINT;
        this.index = 0;
    }

    @Override
    public TripleExpression parse(String expression) throws ParserException {
        try {
            int c = 0;
            int i;
            for (i = 0; i < expression.length(); i++) {
                if (expression.charAt(i) == '(') {
                    c++;
                } else if (expression.charAt(i) == ')') {
                    c--;
                }
                if (c < 0) {
                    break;
                }
            }
            if (c != 0) {
                throw new ParserException(String.format("Wrong bracket at %s", i));
            }
            init(expression);
            getToken();
            return expr(false);
        } catch (ArithmeticException e) {
            throw new ParserException(e);
        }
    }

    public static void main(String[] args) throws ParserException {
        int i = Integer.MIN_VALUE;
        String expression = "-" + i;
        TripleExpression ans = new CheckedParser().parse(expression);
        System.out.println(ans);
        System.out.println(ans.evaluate(0, 0, 0));
    }
}
