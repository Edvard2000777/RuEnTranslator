package translator.validation;

import java.util.Set;

/**
 * Utility class which provides to validate words
 * Word considered Valid if word contains English characters or Russian characters or 0-9 numbers
 */
public class WordsValidator {
    private final Set<Character> ruChars;
    private final Set<Character> enChars;

    public WordsValidator(Set<Character> validRuChars, Set<Character> validEnChars) {
        this.ruChars = validRuChars;
        this.enChars = validEnChars;
    }


    public boolean isValidRuWord(String word) {
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            if (!ruChars.contains(word.charAt(i))) return false;
        }
        return true;
    }

    public boolean isValidEnWord(String word) {
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            if (!enChars.contains(word.charAt(i))) return false;
        }
        return true;
    }


}