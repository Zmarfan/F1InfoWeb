package f1_Info.background.ergast_tasks.fetch_finish_status_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.database.BulkOfWork;
import f1_Info.background.ergast_tasks.ergast.responses.FinishStatusData;
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
        executeBulkOfWork(new BulkOfWork(finishStatusDataList.stream().map(MergeIntoFinishStatusQueryData::new).toList()));
    }
}