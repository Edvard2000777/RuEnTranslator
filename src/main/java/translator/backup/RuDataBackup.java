package translator.backup;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import translator.dto.RuWordDto;
import translator.mapper.RuWordMapper;
import translator.repository.RuWordsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
@Transactional
public class RuDataBackup extends Backup<RuWordsRepository, RuWordDto> {
    private final RuWordMapper mapper;
    RuWordsRepository repository;

    @Autowired
    public RuDataBackup(RuWordMapper mapper, Gson gson, RuWordsRepository repository) {
        super(gson);
        this.mapper = mapper;
        this.repository = repository;
    }

    @Scheduled(fixedRate = 600000)
    public void makeBackup() {
        if (repository == null) throw new RuntimeException("Repository isn't specified");
        List<RuWordDto> allWords = repository.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
        super.saveBackup(allWords);
    }

    /**
     * Provides to load database backup with Ru words from json file
     *
     * @return Optional of words backup
     */
    public Optional<List<RuWordDto>> loadBackup() {
        if (repository == null) throw new RuntimeException("Repository isn't specified");
        List<RuWordDto> backup = null;
        try {
            backup = loadRuDatabaseBackup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(backup);
    }
}
