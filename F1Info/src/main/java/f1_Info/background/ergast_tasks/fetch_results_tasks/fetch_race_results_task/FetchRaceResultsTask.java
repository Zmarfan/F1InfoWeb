package f1_Info.background.ergast_tasks.fetch_results_tasks.fetch_race_results_task;

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
public class FetchRaceResultsTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchRaceResultsTask(
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
        return Tasks.FETCH_RACE_RESULTS_TASK;
    }

    @Override
    protected void runTask() throws SQLException {
        final int nextSeasonToFetch = mDatabase.getNextSeasonToFetchRaceResultsFor();

        final List<ResultDataHolder> raceResults = mErgastProxy.fetchRaceResultsForSeason(nextSeasonToFetch);
        if (!raceResults.isEmpty()) {
            mergeIntoDatabase(raceResults, nextSeasonToFetch);
            mDatabase.setLastFetchedSeason(nextSeasonToFetch);
        }
    }

    private void mergeIntoDatabase(final List<ResultDataHolder> raceResults, final int season) throws SQLException {
        try {
            mDatabase.mergeIntoRaceResultsData(raceResults);
            mLogger.info(
                "mergeIntoDatabase",
                FetchRaceResultsTask.class,
                String.format("Fetched a total of %d race result entries from ergast for season: %d and merged into database", raceResults.size(), season)
            );
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d entries for season %d for race results into the database. Race Results: %s",
                raceResults.size(),
                season,
                ListUtils.listToString(raceResults, ResultDataHolder::toString)
            ), e);
        }
    }
}
