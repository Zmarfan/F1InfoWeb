package f1_Info.background.ergast_tasks.fetch_races_task;

import common.logger.Logger;
import common.utils.ListUtils;
import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.race.RaceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class FetchRacesTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchRacesTask(
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
        final Optional<Integer> nextSeasonToFetch = mDatabase.getNextSeasonToFetchForRaces();
        if (nextSeasonToFetch.isEmpty()) {
            return;
        }

        final List<RaceData> races = mErgastProxy.fetchRacesFromYear(nextSeasonToFetch.get());
        if (!races.isEmpty()) {
            mergeIntoDatabase(races);
            mDatabase.setLastFetchedSeason(nextSeasonToFetch.get());
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_RACES_TASK;
    }

    private void mergeIntoDatabase(final List<RaceData> races) throws SQLException {
        try {
            mDatabase.mergeIntoRacesData(races);
            mLogger.info(
                "mergeIntoDatabase",
                FetchRacesTask.class,
                String.format("Fetched a total of %d race entries from ergast and merged into database", races.size())
            );
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d entries for races into the database. Races: %s",
                races.size(),
                ListUtils.listToString(races, RaceData::toString)
            ), e);
        }
    }
}
