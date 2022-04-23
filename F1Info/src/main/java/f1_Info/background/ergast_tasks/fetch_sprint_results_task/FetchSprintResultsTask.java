package f1_Info.background.ergast_tasks.fetch_sprint_results_task;

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
import java.util.Optional;

@Component
public class FetchSprintResultsTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchSprintResultsTask(
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
        return Tasks.FETCH_SPRINT_RESULTS_TASK;
    }

    @Override
    protected void runTask() throws SQLException {
        final Optional<Integer> nextSeasonToFetch = mDatabase.getNextSeasonToFetchSprintResultsFor();
        if (nextSeasonToFetch.isEmpty()) {
            return;
        }

        final List<ResultDataHolder> sprintResults = mErgastProxy.fetchSprintResultsForSeason(nextSeasonToFetch.get());
        if (!sprintResults.isEmpty()) {
            mergeIntoDatabase(sprintResults, nextSeasonToFetch.get());
            mDatabase.setLastFetchedSeason(nextSeasonToFetch.get());
        }
    }

    private void mergeIntoDatabase(final List<ResultDataHolder> sprintResults, final int season) throws SQLException {
        try {
            mDatabase.mergeIntoSprintResultsData(sprintResults);
            mLogger.info(
                "mergeIntoDatabase",
                FetchSprintResultsTask.class,
                String.format("Fetched a total of %d sprint result entries from ergast for season: %d and merged into database", sprintResults.size(), season)
            );
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d entries for season %d for sprint results into the database. Sprint Results: %s",
                sprintResults.size(),
                season,
                ListUtils.listToString(sprintResults, ResultDataHolder::toString)
            ), e);
        }
    }
}
