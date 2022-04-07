package f1_Info.background;

import f1_Info.configuration.Configuration;
import f1_Info.database.DatabaseBase;
import f1_Info.logger.Logger;

import java.sql.SQLException;

public abstract class TaskDatabase extends DatabaseBase {
    protected TaskDatabase(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public long startBackgroundJob(final Tasks task) throws SQLException {
        return executeBasicQuery(new StartBackgroundJobQueryData(task.getId()));
    }

    public void stopBackgroundJob(final long backgroundId, final Exception exception) throws SQLException {
        executeVoidQuery(new StopBackgroundJobQueryData(backgroundId, exception == null ? null : exception.getMessage()));
    }
}
