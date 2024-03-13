package translator.exceptions.backupExceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseLoadBackupException extends LoadBackupException {
    public DatabaseLoadBackupException() {
        super("Exception for database backup loading at " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss")));
    }

    public DatabaseLoadBackupException(String database) {
        super("Exception for database (" + database + ") backup loading at " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss")));
    }
}
