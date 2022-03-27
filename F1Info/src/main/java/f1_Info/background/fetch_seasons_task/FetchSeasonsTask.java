package f1_Info.background.fetch_seasons_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.SeasonData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class FetchSeasonsTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchSeasonsTask(
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
        final List<SeasonData> seasons = mErgastProxy.fetchAllSeasons();
        if (!seasons.isEmpty()) {
            mDatabase.mergeIntoSeasonsData(seasons);
            mLogger.info(
                "runTask",
                FetchSeasonsTask.class,
                String.format("Fetched a total of %d season entries from ergast and merged into database", seasons.size())
            );
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_SEASONS_TASK;
    }
}