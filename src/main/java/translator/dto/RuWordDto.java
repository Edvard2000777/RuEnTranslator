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
public class RuWordDto extends AbstractDto {

    private WordType wordType = WordType.RU;
    @ValidWord
    private String word;
    private Set<EnWordDto> enWords;

    public RuWordDto(String ruWord) {
        this.word = ruWord;
    }

    public void addEnWord(String word) {
        if (enWords == null) {
            enWords = new HashSet<>();
        }
        enWords.add(new EnWordDto(word));
    }

    @Override
    public String toString() {
        return "RuWordDto{" +
                "wordType=" + wordType +
                ", word='" + word + '}';
    }
}
