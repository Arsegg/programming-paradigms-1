package expression.generic;

public class GenericParserException extends Exception {
    public GenericParserException() {
    }

    public GenericParserException(String message) {
        super(message);
    }

    public GenericParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericParserException(Throwable cause) {
        super(cause);
    }

    public GenericParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
