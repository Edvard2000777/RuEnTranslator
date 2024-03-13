package translator.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import translator.dto.EnWordDto;
import translator.dto.RuWordDto;
import translator.entity.EnWord;
import translator.entity.RuWord;
import translator.mapper.RuWordMapper;
import translator.repository.RuWordsRepository;

import java.util.HashSet;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTestsRunner {
    @Mock
    private RuWordsRepository ruWordsRepository;
    @Mock
    private RuWordMapper mapper;


    @InjectMocks
    private RuWordService ruWordService;


    @Test
    public void should_save_Ru_word_and_return_saved_word() {
        RuWordDto ruWordDto = new RuWordDto("получить");
        ruWordDto.addEnWord("get");
//
//        RuWord ruWord = new RuWord(ruWordDto.getWord());
//        ruWord.setEnWords(ruWordDto.getEnWords().stream()
//                .map(x -> new EnWord(x.getWord())).collect(Collectors.toSet()));
//
//        when(mapper.toEntity(ruWordDto)).thenReturn(ruWord);
//
//        when(ruWordsRepository.save(ruWord)).thenReturn(ruWord);
        RuWordDto savedWord = ruWordService.save(ruWordDto);
//        assertThat(foundedWord).isEqualTo(ruWordDto);


    }

    @Test
    public void should_save_En_word_and_return_saved_word() {

    }

}
