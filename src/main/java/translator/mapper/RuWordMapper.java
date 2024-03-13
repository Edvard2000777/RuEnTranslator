package translator.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import translator.dto.EnWordDto;
import translator.dto.RuWordDto;
import translator.entity.RuWord;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
public class RuWordMapper implements Mapper<RuWord, RuWordDto> {

    private final ModelMapper mapper;

    @Autowired
    public RuWordMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(RuWord.class, RuWordDto.class)
                .addMappings(m -> m.skip(RuWordDto::setEnWords)).setPostConverter(convertToDto());
    }

    @Override
    public RuWord toEntity(RuWordDto wordDto) {
        if (wordDto == null) return null;
        RuWord ruWord = new RuWord();
        mapper.map(wordDto, ruWord);
        return ruWord;
    }

    @Override
    public RuWordDto toDto(RuWord word) {
        if (word == null) return null;
        RuWordDto ruWordDto = new RuWordDto();
        mapper.map(word, ruWordDto);
        return ruWordDto;
    }

    private Converter<RuWord, RuWordDto> convertToDto() {
        return context -> {
            RuWord source = context.getSource();
            RuWordDto destination = context.getDestination();
            if (source.getEnWords() != null) {
                destination.setEnWords((source.getEnWords().stream()
                        .map(x -> new EnWordDto(x.getWord())).collect(Collectors.toSet())));
            }
            return context.getDestination();
        };
    }

}
