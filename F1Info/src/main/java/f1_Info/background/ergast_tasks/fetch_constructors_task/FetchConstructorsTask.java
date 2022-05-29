package f1_Info.background.ergast_tasks.fetch_constructors_task;

import common.logger.Logger;
import common.utils.ListUtils;
import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class FetchConstructorsTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchConstructorsTask(
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
        final List<ConstructorData> constructors = mErgastProxy.fetchAllConstructors();
        if (!constructors.isEmpty()) {
            mergeIntoDatabase(constructors);
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_CONSTRUCTORS_TASK;
    }

    private void mergeIntoDatabase(final List<ConstructorData> constructors) throws SQLException {
        try {
            mDatabase.mergeIntoConstructorsData(constructors);
            mLogger.info(
                "mergeIntoDatabase",
                FetchConstructorsTask.class,
                String.format("Fetched a total of %d constructor entries from ergast and merged into database", constructors.size())
            );
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d entries for constructors into the database. Constructors: %s",
                constructors.size(),
                ListUtils.listToString(constructors, ConstructorData::toString)
            ), e);
        }
    }
}
