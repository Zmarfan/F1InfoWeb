package f1_Info.background.fetch_drivers_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.DriverData;
import f1_Info.logger.Logger;
import f1_Info.utils.ListUtils;
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
        final List<DriverData> drivers = mErgastProxy.fetchAllDrivers();
        if (!drivers.isEmpty()) {
            mergeIntoDatabase(drivers);
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_DRIVERS_TASK;
    }

    private void mergeIntoDatabase(final List<DriverData> drivers) throws SQLException {
        try {
            mDatabase.mergeIntoDriversData(drivers);
            mLogger.info(
                "mergeIntoDatabase",
                FetchDriversTask.class,
                String.format("Fetched a total of %d driver entries from ergast and merged into database", drivers.size())
            );
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d entries for drivers into the database. Drivers: %s",
                drivers.size(),
                ListUtils.listToString(drivers, DriverData::toString)
            ), e);
        }
    }
}

