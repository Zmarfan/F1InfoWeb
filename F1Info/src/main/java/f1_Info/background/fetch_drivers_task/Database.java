package f1_Info.background.fetch_drivers_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.database.DatabaseBulkOfWork;
import f1_Info.ergast.responses.DriverData;
import f1_Info.logger.Logger;
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
        final DatabaseBulkOfWork bulkOfWork = new DatabaseBulkOfWork();
        bulkOfWork.add(driverDataList.stream().map(driverData -> new MergeIntoDriverDataQueryData(
            driverData.getDriverIdentifier(),
            driverData.getPermanentNumber().orElse(null),
            driverData.getCode().orElse(null),
            driverData.getFirstName(),
            driverData.getLastName(),
            driverData.getDateOfBirth(),
            driverData.getCountry(),
            driverData.getWikipediaUrl()
        )).toList());
        executeBulkOfWork(bulkOfWork);
    }
}

