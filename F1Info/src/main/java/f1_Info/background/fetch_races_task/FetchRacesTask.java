package f1_Info.background.fetch_races_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.race.RaceData;
import f1_Info.logger.Logger;
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
            mDatabase.mergeIntoRacesData(races);
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_RACES_TASK;
    }
}
