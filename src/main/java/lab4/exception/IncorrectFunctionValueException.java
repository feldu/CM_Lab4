package lab4.exception;

public class IncorrectFunctionValueException extends RuntimeException {
    public IncorrectFunctionValueException() {
        super();
    }

    public IncorrectFunctionValueException(String message) {
        super(message);
    }
}
