package gui.exceptions;

public class CommandControllerException extends RuntimeException {
    public CommandControllerException(String message) {
        super(message);
    }

    public CommandControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
