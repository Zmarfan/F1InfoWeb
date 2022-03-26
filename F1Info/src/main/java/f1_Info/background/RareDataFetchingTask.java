package f1_Info.background;

import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.ergast.responses.DriverData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class RareDataFetchingTask {
    private static final String TASK_NAME = "RARE_DATA_FETCHING_TASK";

    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;
    private final Logger mLogger;

    @Autowired
    public RareDataFetchingTask(
        ErgastProxy ergastProxy,
        Database database,
        Logger logger
    ) {
        mErgastProxy = ergastProxy;
        mDatabase = database;
        mLogger = logger;
    }

    public void run() {
        final Optional<Long> id = startBackgroundJob();
        if (id.isEmpty()) {
            return;
        }

        Exception exception = null;
        try {
            fetchConstructors();
            fetchDrivers();
        } catch (final Exception e) {
            exception = e;
            mLogger.severe("run", RareDataFetchingTask.class, "Failed to complete rare Data Fetching Task", e);
        } finally {
            stopBackgroundJob(id.get(), exception);
        }
    }

    private void fetchConstructors() throws SQLException {
        final List<ConstructorData> constructors = mErgastProxy.fetchAllConstructors();
        if (!constructors.isEmpty()) {
            mDatabase.mergeIntoConstructorsData(constructors);
        }
    }

    private void fetchDrivers() throws SQLException {
        final List<DriverData> drivers = mErgastProxy.fetchAllDrivers();
        if (!drivers.isEmpty()) {
            mDatabase.mergeIntoDriversData(drivers);
        }
    }

    private Optional<Long> startBackgroundJob() {
        try {
            return Optional.of(mDatabase.startBackgroundJob(TASK_NAME));
        } catch (final Exception e) {
            mLogger.severe("startBackgroundJob", RareDataFetchingTask.class, "Failed to init rare Data Fetching Task", e);
            return Optional.empty();
        }
    }

    private void stopBackgroundJob(final long backgroundId, final Exception exception) {
        try {
            mDatabase.stopBackgroundJob(backgroundId, exception);
        } catch (final Exception e) {
            mLogger.severe("stopBackgroundJob", RareDataFetchingTask.class, "Failed to finish rare Data Fetching Task", e);
        }
    }
}
