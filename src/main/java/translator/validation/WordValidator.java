package translator.validation;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class WordValidator implements ConstraintValidator<ValidWord, String> {
    private WordsValidator validator;

    @PostConstruct
    private void init() {
        Set<Character> validChars = new HashSet<>();
        for (int i = 97; i <= 122; i++) {
            validChars.add((char) i);
        }
        for (int i = 1072; i <= 1103; i++) {
            validChars.add((char) i);
        }
        for (int i = 48; i <= 57; i++) {
            validChars.add((char) i);
        }
        validator = new WordsValidator(validChars, validChars);
    }

    @Override
    public boolean isValid(String word, ConstraintValidatorContext constraintValidatorContext) {
        return word != null && (validator.isValidRuWord(word) || validator.isValidEnWord(word));
    }
}
