package translator.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import translator.dto.EnWordDto;
import translator.dto.RuWordDto;
import translator.entity.EnWord;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
public class EnWordMapper implements Mapper<EnWord, EnWordDto> {

    private final ModelMapper mapper;

    @Autowired
    public EnWordMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Set rule of word mapping
     */
    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(EnWord.class, EnWordDto.class)
                .addMappings(m -> m.skip(EnWordDto::setRuWords)).setPostConverter(convertToDto());
    }

    /**
     * Provides to convert specified DTO to Entity
     *
     * @param wordDto dto to convert to Entity class
     * @return Entity class of specified DTO class
     */
    @Override
    public EnWord toEntity(EnWordDto wordDto) {
        if (wordDto == null) return null;
        EnWord enWord = new EnWord();
        mapper.map(wordDto, enWord);
        return enWord;
    }

    /**
     * Provides to convert specified Entity to DTO
     *
     * @param word word to convert to DTO class
     * @return Dto class of specified Entity class
     */
    @Override
    public EnWordDto toDto(EnWord word) {
        if (word == null) return null;
        EnWordDto enWordDto = new EnWordDto();
        mapper.map(word, enWordDto);
        return enWordDto;
    }

    private Converter<EnWord, EnWordDto> convertToDto() {
        return context -> {
            EnWord source = context.getSource();
            EnWordDto destination = context.getDestination();
            if (source.getRuWords() != null) {
                destination.setRuWords((source.getRuWords().stream()
                        .map(x -> new RuWordDto(x.getWord())).collect(Collectors.toSet())));
            }
            return context.getDestination();
        };
    }

}
