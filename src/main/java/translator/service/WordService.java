package translator.service;

import org.springframework.stereotype.Component;
import translator.dto.AbstractDto;

import java.util.HashSet;
import java.util.Set;

@Component
public abstract class WordService<E extends AbstractDto> implements AbstractService<E> {
    private Set<Character> ruChars;
    private Set<Character> enChars;

    public WordService() {
        this.ruChars = null;
        this.enChars = null;
    }

    /**
     * Init set of characters to check words
     */
    private void initCharSets() {
        enChars = new HashSet<>();
        for (int i = 97; i <= 122; i++) {
            enChars.add((char) i);
        }
        ruChars = new HashSet<>();
        for (int i = 1072; i <= 1103; i++) {
            ruChars.add((char) i);
        }
    }

    /**
     * Provides to validate Russian word to contains all Ru characters
     *
     * @param word word to validate
     * @return true if word is invalid otherwise false
     */
    public boolean isInvalidRuWord(String word) {
        if (ruChars == null) initCharSets();
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            if (!ruChars.contains(word.charAt(i))) return true;
        }
        return false;
    }

    /**
     * Provides to validate English word to contains all En characters
     *
     * @param word word to validate
     * @return true if word is invalid otherwise false
     */
    public boolean isInvalidEnWord(String word) {
        if (enChars == null) initCharSets();
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            if (!enChars.contains(word.charAt(i))) return true;
        }
        return false;
    }


}
