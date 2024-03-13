package translator.backup;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import translator.dto.EnWordDto;
import translator.mapper.EnWordMapper;
import translator.repository.EnWordsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
@Transactional
public class EnDataBackup extends Backup<EnWordsRepository, EnWordDto> {
    private final EnWordMapper mapper;
    EnWordsRepository repository;

    @Autowired
    public EnDataBackup(EnWordMapper mapper, Gson gson, EnWordsRepository repository) {
        super(gson);
        this.mapper = mapper;
        this.repository = repository;
    }

    @Scheduled(fixedRate = 600000)
    public void makeBackup() {
        if (repository == null) throw new RuntimeException("Repository isn't specified");
        List<EnWordDto> allWords = repository.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
        super.saveBackup(allWords);
    }

    /**
     * Provides to load database backup with En words from json file
     *
     * @return Optional of words backup
     */
    public Optional<List<EnWordDto>> loadBackup() {
        if (repository == null) throw new RuntimeException("Repository isn't specified");
        List<EnWordDto> backup = null;
        try {
            backup = loadEnDatabaseBackup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(backup);
    }
}
