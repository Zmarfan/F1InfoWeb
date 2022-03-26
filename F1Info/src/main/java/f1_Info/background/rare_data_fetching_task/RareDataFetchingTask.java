package f1_Info.background.rare_data_fetching_task;

import f1_Info.background.TaskWrapper;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.ergast.responses.DriverData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class RareDataFetchingTask extends TaskWrapper {
    private static final String TASK_NAME = "RARE_DATA_FETCHING_TASK";

    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public RareDataFetchingTask(
        ErgastProxy ergastProxy,
        Database database,
        Logger logger
    ) {
        super(database, logger, TASK_NAME);
        mErgastProxy = ergastProxy;
        mDatabase = database;
    }

    @Override
    protected void runTask() throws SQLException {
        fetchConstructors();
        fetchDrivers();
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
