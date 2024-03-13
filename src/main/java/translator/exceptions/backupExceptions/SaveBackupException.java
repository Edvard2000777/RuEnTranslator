package translator.exceptions.backupExceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveBackupException extends RuntimeException {
    public SaveBackupException() {
        super("Exception occurred while saving");
    }

    public SaveBackupException(String object) {
        super("Errors occurred while saving object: " + object + " at " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss")));
    }
}
