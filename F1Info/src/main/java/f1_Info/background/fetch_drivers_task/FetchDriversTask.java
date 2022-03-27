package f1_Info.background.fetch_drivers_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.background.rare_data_fetching_task.Database;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.DriverData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class FetchDriversTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchDriversTask(
        ErgastProxy ergastProxy,
        Database database,
        Logger logger
    ) {
        super(logger, database);
        mErgastProxy = ergastProxy;
        mDatabase = database;
    }

    @Override
    protected void runTask() throws SQLException {
        fetchDrivers();
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_DRIVERS_TASK;
    }

    private void fetchDrivers() throws SQLException {
        final List<DriverData> drivers = mErgastProxy.fetchAllDrivers();
        if (!drivers.isEmpty()) {
            mDatabase.mergeIntoDriversData(drivers);
        }
    }
}

