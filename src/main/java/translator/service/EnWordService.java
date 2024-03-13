package translator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import translator.backup.EnDataBackup;
import translator.dto.EnWordDto;
import translator.dto.RuWordDto;
import translator.entity.EnWord;
import translator.exceptions.InvalidWordException;
import translator.exceptions.WordNotFoundException;
import translator.mapper.EnWordMapper;
import translator.repository.EnWordsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnWordService extends WordService<EnWordDto> {

    private final EnWordMapper mapper;
    private final EnWordsRepository repository;
    private final EnDataBackup backup;

    @Autowired
    public EnWordService(EnWordMapper mapper, EnWordsRepository repository, EnDataBackup backup) {
        this.mapper = mapper;
        this.repository = repository;
        this.backup = backup;
    }

    /**
     * Provides to save specified specified word
     * If specified specified word present in database -> updates dto in the database
     * Otherwise saves specified word
     *
     * @param dto dto to save
     * @return saved dto
     * @throws InvalidWordException if word is invalid(contains non english characters)
     */
    @Override
    @Transactional
    public EnWordDto save(EnWordDto dto) {
        if (isInvalidEnWord(dto.getWord())) throw new InvalidWordException(dto.getWord());
        EnWord temp = repository.get(mapper.toEntity(dto));
        if (temp != null) return update(mapper.toDto(temp), dto);
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    /**
     * Provides to get specified word from database by the word name (String)
     *
     * @param name word name to search in database
     * @return DTO of specified word name from database
     * @throws InvalidWordException if word is invalid(contains non english characters)
     */
    @Override
    @Transactional
    public EnWordDto getByWordName(String name) {
        if (isInvalidEnWord(name)) throw new InvalidWordException(name);
        return mapper.toDto(repository.get(new EnWord(name)));
    }

    /**
     * Provides to delete specified word from database if
     *
     * @param dto word to delete
     * @return deleted word DTO
     * @throws InvalidWordException if word is invalid(contains non english characters)
     */
    @Override
    @Transactional
    public EnWordDto delete(EnWordDto dto) {
        if (isInvalidEnWord(dto.getWord())) throw new InvalidWordException(dto.getWord());
        EnWord temp = repository.get(mapper.toEntity(dto));
        if (temp == null) return null;
        else backup.serialize(mapper.toDto(temp));
        return mapper.toDto(repository.delete(temp));
    }

    /**
     * Provides to update specified word name in the database
     *
     * @param replaced  word id database which will be replaced
     * @param toReplace word which replace [replaced] word
     * @return replaced word
     * @throws InvalidWordException if word is invalid(contains non english characters)
     */
    @Override
    @Transactional
    public EnWordDto update(EnWordDto replaced, EnWordDto toReplace) {
        if (isInvalidEnWord(replaced.getWord()) || isInvalidEnWord(toReplace.getWord())) {
            throw new InvalidWordException();
        }
        EnWordDto word = delete(mapper.toDto(repository.get(mapper.toEntity(replaced))));
        if (word.getWord() == null) throw new WordNotFoundException(replaced.getWord());
        toReplace = mergeWords(word, toReplace);
        return mapper.toDto(repository.save(mapper.toEntity(toReplace)));
    }

    /**
     * Provides to append new translation for word
     *
     * @param dto            word for which to append new translate
     * @param newTranslation new translate for specified word
     * @return updated word
     * @throws InvalidWordException if word is invalid(contains non english characters)
     */
    @Override
    @Transactional
    public EnWordDto addNewTranslation(EnWordDto dto, String newTranslation) {
        if (isInvalidEnWord(dto.getWord()) || isInvalidEnWord(newTranslation)) {
            throw new InvalidWordException();
        }
        EnWord word = repository.get(mapper.toEntity(dto));
        if (word == null) throw new WordNotFoundException(dto.getWord());
        word.addWord(newTranslation);
        return mapper.toDto(repository.save(word));
    }

    /**
     * Provides to get all words from database
     *
     * @return list of all words from database
     */
    @Override
    @Transactional
    public List<EnWordDto> getAll() {
        return repository.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    /**
     * Provides to translate word sequence Russian to English
     * If word from sequence presents in the database accordingly word will be translated
     * Otherwise word stay not translated
     *
     * @param sequence sequence of words to translate
     * @return list of all translated words in sequence
     * @throws InvalidWordException if sequence is null
     */
    @Transactional
    public List<String> translateSequence(String sequence) {
        if (sequence == null) {
            throw new InvalidWordException();
        }
        List<String> translatedSequence = new ArrayList<>(Arrays.asList(sequence.split(" ")));
        for (int i = 0; i < translatedSequence.size(); i++) {
            String word = translatedSequence.get(i);
            if (!isInvalidEnWord(word)) {
                EnWord tempWord = repository.get(new EnWord(word));
                if (tempWord == null) continue;
                translatedSequence.set(i, tempWord.getRuWords().get(0).getWord());
            }
        }
        return translatedSequence;
    }

    /**
     * Provides to restore all words from backup in database
     */
    @Transactional
    public void restoreBackup() {
        Optional<List<EnWordDto>> removed = backup.deserializeAll();
        removed.ifPresent(words -> words.forEach(this::save));
    }

    /**
     * Provides to make backup of all database
     */
    public void backupData() {
        backup.makeBackup();
    }

    /**
     * Provides to merge between two words
     *
     * @param replaced  word from which carried in all data to toReplace word
     * @param toReplace word to which moved all data from replaced word
     * @return word which contains all data from 2 words
     */
    private EnWordDto mergeWords(EnWordDto replaced, EnWordDto toReplace) {
        if (replaced == null || toReplace == null) throw new NullPointerException("Words mustn't be not null");
        if (replaced.getRuWords() == null) return toReplace;
        for (RuWordDto dto : replaced.getRuWords()) {
            toReplace.addRuWord(dto.getWord());
        }
        return toReplace;
    }
}
