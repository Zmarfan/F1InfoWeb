package f1_Info.background.ergast_tasks.fetch_finish_status_task;

import common.constants.f1.FinishStatus;
import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.FinishStatusData;
import common.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import common.utils.ListUtils;

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
            mergeIntoDatabase(finishStatuses);
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_FINISH_STATUS_TASK;
    }

    private void checkFetchedFinishStatuses(final List<FinishStatusData> finishStatusData) {
        finishStatusData
            .stream()
            .filter(statusData -> !FinishStatus.exists(statusData.getStatus()))
            .forEach(entry -> mLogger.warning("checkFetchedFinishStatuses", FetchFinishStatusTask.class, String.format(
                "Fetched finish status data with name: %s that is not implemented in enum," +
                    "will store it in database but will not be able to read it properly",
                entry.getStatus()
            )));
    }

    private void mergeIntoDatabase(final List<FinishStatusData> finishStatuses) throws SQLException {
        try {
            mDatabase.mergeIntoFinishStatusData(finishStatuses);
            mLogger.info(
                "mergeIntoDatabase",
                FetchFinishStatusTask.class,
                String.format("Fetched a total of %d finish statuses entries from ergast and merged into database", finishStatuses.size())
            );
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d entries for finish statuses into the database. Data: %s",
                finishStatuses.size(),
                ListUtils.listToString(finishStatuses, FinishStatusData::toString)
            ), e);
        }
    }
}
