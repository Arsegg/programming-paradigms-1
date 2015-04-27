package expression;

public class ExpressionParser implements Parser {
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
        if (Character.isDigit(ch) || ch == '.') {
            StringBuilder number = new StringBuilder();
            number.append('-');
            number.append(ch);
            while (Character.isDigit(get()) || get() == '.') {
                number.append(pop());
            }
            numberValue = Integer.parseInt(number.toString());
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
            return currentToken = Token.NAME;
        }
        if (ch == '*') {
            return currentToken = Token.MUL;
        }
        if (ch == '/') {
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
        assert false;
        return null;
    }

    private TripleExpression prim(boolean get) {
        if (get) {
            getToken();
        }

        if (currentToken == Token.NUMBER) {
            TripleExpression number = new Const(-numberValue);
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
            assert currentToken == Token.RP;
            getToken();
            return expression;
        }
        if (currentToken == Token.ABS) {
            return new Abs(prim(true));
        }
        if (currentToken == Token.SQUARE) {
            return new Square(prim(true));
        }
        assert false;
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

    private TripleExpression expr(boolean get) {
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
    public TripleExpression parse(String expression) {
        init(expression);
        getToken();
        return expr(false);
    }
}
