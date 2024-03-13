package translator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import translator.validation.ValidWord;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnWordDto extends AbstractDto {

    private WordType wordType = WordType.EN;
    @ValidWord
    private String word;
    private Set<RuWordDto> ruWords;

    public EnWordDto(String enWord) {
        this.word = enWord;
    }

    public void addRuWord(String word) {
        if (ruWords == null) {
            ruWords = new HashSet<>();
        }
        ruWords.add(new RuWordDto(word));
    }

    @Override
    public String toString() {
        return "EnWordDto{" +
                "wordType=" + wordType +
                ", word='" + word + '}';
    }
}
