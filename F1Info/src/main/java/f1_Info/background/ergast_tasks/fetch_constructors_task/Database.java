package f1_Info.background.ergast_tasks.fetch_constructors_task;

import common.configuration.Configuration;
import common.logger.Logger;
import database.BulkOfWork;
import f1_Info.background.TaskDatabase;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "FetchConstructorsTaskDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void mergeIntoConstructorsData(final List<ConstructorData> constructorDataList) throws SQLException {
        executeBulkOfWork(new BulkOfWork(constructorDataList.stream().map(MergeIntoConstructorDataQueryData::new).toList()));
    }
}