package f1_Info.background.fetch_finish_status_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.database.DatabaseBulkOfWork;
import f1_Info.ergast.responses.FinishStatusData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "FetchFinishStatusTaskDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void mergeIntoFinishStatusData(final List<FinishStatusData> finishStatusDataList) throws SQLException {
        final DatabaseBulkOfWork bulkOfWork = new DatabaseBulkOfWork();
        bulkOfWork.add(finishStatusDataList.stream().map(data -> new MergeIntoFinishStatusQueryData(data.getId(), data.getStatus())).toList());
        executeBulkOfWork(bulkOfWork);
    }
}