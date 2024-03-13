package translator.exceptions;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(String word) {
        super("Word " + word + " not found");
    }

}
