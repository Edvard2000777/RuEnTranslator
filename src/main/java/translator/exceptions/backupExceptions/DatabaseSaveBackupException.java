package translator.exceptions.backupExceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseSaveBackupException extends SaveBackupException {
    public DatabaseSaveBackupException() {
        super("Exception for database backup saving at " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss")));
    }

    public DatabaseSaveBackupException(String database) {
        super("Exception for database (" + database + ") backup saving at " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss")));
    }
}
