package f1_Info.background.fetch_constructors_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.database.DatabaseBulkOfWork;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.logger.Logger;
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
        final DatabaseBulkOfWork bulkOfWork = new DatabaseBulkOfWork();
        bulkOfWork.add(constructorDataList.stream().map(constructorData -> new MergeIntoConstructorDataQueryData(
            constructorData.getConstructorIdentifier(),
            constructorData.getName(),
            constructorData.getCountry(),
            constructorData.getWikipediaUrl()
        )).toList());
        executeBulkOfWork(bulkOfWork);
    }
}