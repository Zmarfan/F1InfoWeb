package f1_Info.background;

import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.ergast.responses.DriverData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class RareDataFetchingTask {
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
        try {
            mLogger.info("run", RareDataFetchingTask.class, "Started fetching rare data");

            fetchConstructors();
            fetchDrivers();
        } catch (final Exception e) {
            mLogger.severe("run", RareDataFetchingTask.class, "Failed to complete rare Data Fetching Task", e);
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
}
