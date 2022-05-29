package f1_Info.background;

import common.configuration.Configuration;
import common.logger.Logger;
import database.DatabaseBase;

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
