package translator.exceptions;

public class InvalidWordException extends RuntimeException {
    public InvalidWordException(String word) {
        super("Word " + word + " contains invalid characters");
    }

    public InvalidWordException() {
        super("Word contains invalid characters");
    }
}
