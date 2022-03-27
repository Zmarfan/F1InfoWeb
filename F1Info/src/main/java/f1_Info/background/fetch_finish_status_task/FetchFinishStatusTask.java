package f1_Info.background.fetch_finish_status_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.constants.FinishStatus;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.FinishStatusData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class FetchFinishStatusTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchFinishStatusTask(
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
        final List<FinishStatusData> finishStatuses = mErgastProxy.fetchAllFinishStatuses();
        if (!finishStatuses.isEmpty()) {
            checkFetchedFinishStatuses(finishStatuses);

            mDatabase.mergeIntoFinishStatusData(finishStatuses);
            mLogger.info(
                "runTask",
                FetchFinishStatusTask.class,
                String.format("Fetched a total of %d finish statuses entries from ergast and merged into database", finishStatuses.size())
            );
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_FINISH_STATUS_TASK;
    }

    private void checkFetchedFinishStatuses(final List<FinishStatusData> finishStatusData) {
        final List<FinishStatusData> unsupportedData = finishStatusData.stream().filter(statusData -> !FinishStatus.exists(statusData.getId())).toList();
        unsupportedData.forEach(entry -> mLogger.warning("checkFetchedFinishStatuses", FetchFinishStatusTask.class, String.format(
            "Fetched finish status data with id: %d and name: %s that is not implemented in enum," +
                "will store it in database but will not be able to read it properly",
            entry.getId(),
            entry.getStatus()
        )));
    }
}
