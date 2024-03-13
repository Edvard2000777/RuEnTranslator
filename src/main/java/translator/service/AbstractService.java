package translator.service;

import org.springframework.transaction.annotation.Transactional;
import translator.dto.AbstractDto;
import translator.dto.EnWordDto;
import translator.entity.AbstractWord;

import java.util.List;

public interface AbstractService<E extends AbstractDto> {

    E save(E dto);
    E getByWordName(String word);
    E delete(E dto);
    E update(E replaced, E String);
    E addNewTranslation(E word, String newTranslation);
    List<E> getAll();


}
