package f1_Info.background.ergast_tasks.fetch_drivers_task;

import common.configuration.Configuration;
import common.logger.Logger;
import database.BulkOfWork;
import f1_Info.background.TaskDatabase;
import f1_Info.background.ergast_tasks.ergast.responses.DriverData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "FetchDriversTaskDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void mergeIntoDriversData(final List<DriverData> driverDataList) throws SQLException {
        executeBulkOfWork(new BulkOfWork(driverDataList.stream().map(MergeIntoDriverDataQueryData::new).toList()));
    }
}

