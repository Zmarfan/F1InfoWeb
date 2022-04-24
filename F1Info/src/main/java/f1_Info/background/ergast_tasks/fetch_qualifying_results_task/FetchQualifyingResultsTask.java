package f1_Info.background.ergast_tasks.fetch_qualifying_results_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.results.ResultDataHolder;
import f1_Info.logger.Logger;
import f1_Info.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class FetchQualifyingResultsTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchQualifyingResultsTask(
        ErgastProxy ergastProxy,
        Database database,
        Logger logger
    ) {
        super(logger, database);
        mErgastProxy = ergastProxy;
        mDatabase = database;
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_QUALIFYING_RESULTS_TASK;
    }

    @Override
    protected void runTask() throws SQLException {
        final int nextSeasonToFetch = mDatabase.getNextSeasonToFetchQualifyingResultsFor();

        final List<ResultDataHolder> qualifyingResults = mErgastProxy.fetchQualifyingResultsForSeason(nextSeasonToFetch);
        if (!qualifyingResults.isEmpty()) {
            mergeIntoDatabase(qualifyingResults, nextSeasonToFetch);
            mDatabase.setLastFetchedSeason(nextSeasonToFetch);
        }
    }

    private void mergeIntoDatabase(final List<ResultDataHolder> qualifyingResults, final int season) throws SQLException {
        try {
            mDatabase.mergeIntoQualifyingResultsData(qualifyingResults);
            mLogger.info("mergeIntoDatabase", FetchQualifyingResultsTask.class, String.format(
                "Fetched a total of %d qualifying result entries from ergast for season: %d and merged into database",
                qualifyingResults.size(),
                season
            ));
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d entries for season %d for qualifying results into the database. Qualifying Results: %s",
                qualifyingResults.size(),
                season,
                ListUtils.listToString(qualifyingResults, ResultDataHolder::toString)
            ), e);
        }
    }
}
