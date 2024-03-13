package translator.exceptions.backupExceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoadBackupException extends RuntimeException {
    public LoadBackupException() {
        super("Exception occurred while loading backup");
    }

    public LoadBackupException(String object) {
        super("Errors occurred while loading object: " + object + " at " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss")));
    }
}
