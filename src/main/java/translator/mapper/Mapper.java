package translator.mapper;

import translator.dto.AbstractDto;
import translator.entity.AbstractWord;

public interface Mapper<E extends AbstractWord, D extends AbstractDto> {

    AbstractWord toEntity(D wordDto);

    AbstractDto toDto(E word);

}

