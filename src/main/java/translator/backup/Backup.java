package translator.backup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import translator.dto.AbstractDto;
import translator.dto.EnWordDto;
import translator.dto.RuWordDto;
import translator.entity.AbstractWord;
import translator.exceptions.backupExceptions.DatabaseLoadBackupException;
import translator.exceptions.backupExceptions.DatabaseSaveBackupException;
import translator.exceptions.backupExceptions.SaveBackupException;
import translator.repository.AbstractRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Used for making backup of removed words and if necessary and clearing backup each 30 minutes
 */
public class Backup<T extends AbstractRepository<? extends AbstractWord>, E extends AbstractDto> {
    private final String backupFile;
    private final String removedBackupFile;
    private final Gson JSONSerializer;

    public Backup(Gson JSONSerializer) {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\backup\\";
        this.backupFile = path + "databaseBackup.json";
        this.removedBackupFile = path + "removed.txt";
        this.JSONSerializer = JSONSerializer;
    }

    void saveBackup(List<? extends AbstractDto> toSave) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(backupFile), StandardCharsets.UTF_8)) {
            JSONSerializer.toJson(toSave, writer);
        } catch (IOException exception) {
            throw new DatabaseSaveBackupException();
        }
    }

    List<EnWordDto> loadEnDatabaseBackup() {
        try {
            return JSONSerializer.fromJson(new FileReader(backupFile), new TypeToken<List<EnWordDto>>() {
            }.getType());
        } catch (IOException exception) {
            throw new DatabaseLoadBackupException();
        }
    }

    List<RuWordDto> loadRuDatabaseBackup() {
        try {
            return JSONSerializer.fromJson(new FileReader(backupFile), new TypeToken<List<RuWordDto>>() {
            }.getType());
        } catch (IOException exception) {
            throw new DatabaseLoadBackupException();
        }
    }

    public void clearDatabaseBackup() throws IOException {
        new FileOutputStream(backupFile).close();
    }

    public void clearBackup() throws IOException {
        new FileOutputStream(removedBackupFile).close();
    }

    /**
     * Provides to serialize specified DTO class
     *
     * @param dto dto to serialize
     */
    public void serialize(E dto) {
        if (dto == null) return;
        Optional<List<E>> backup = deserializeAll();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(removedBackupFile))) {
            List<E> list = backup.orElseGet(() -> new ArrayList<>(Collections.singletonList(dto)));
            list.add(dto);
            out.writeObject(list);

        } catch (IOException exception) {
            throw new SaveBackupException();
        }
    }

    /**
     * Provides to deserialize specified DTO class
     *
     * @return Optional of DTO class
     */
    @SuppressWarnings("unchecked")
    public Optional<List<E>> deserializeAll() {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(removedBackupFile))) {
            return Optional.of((List<E>) input.readObject());
        } catch (IOException | ClassNotFoundException exception) {
            return Optional.empty();
        }
    }

}
