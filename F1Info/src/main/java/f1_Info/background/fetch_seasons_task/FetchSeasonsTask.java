package f1_Info.background.fetch_seasons_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.background.test_query_datas.TestRecord;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.SeasonData;
import f1_Info.logger.Logger;
import f1_Info.utils.ListUtils;
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
        if (true) {
            final TestRecord test = mDatabase.testCall();
            return;
        }

        final List<SeasonData> seasons = mErgastProxy.fetchAllSeasons();
        if (!seasons.isEmpty()) {
            mergeIntoDatabase(seasons);
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_SEASONS_TASK;
    }

    private void mergeIntoDatabase(final List<SeasonData> seasons) throws SQLException {
        try {
            mDatabase.mergeIntoSeasonsData(seasons);
            mLogger.info(
                "mergeIntoDatabase",
                FetchSeasonsTask.class,
                String.format("Fetched a total of %d season entries from ergast and merged into database", seasons.size())
            );
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d entries for seasons into the database. Seasons: %s",
                seasons.size(),
                ListUtils.listToString(seasons, SeasonData::toString)
            ), e);
        }
    }
}