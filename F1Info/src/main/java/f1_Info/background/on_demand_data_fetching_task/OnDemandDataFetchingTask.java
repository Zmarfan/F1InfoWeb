package f1_Info.background.on_demand_data_fetching_task;

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
public class OnDemandDataFetchingTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public OnDemandDataFetchingTask(
        ErgastProxy ergastProxy,
        Database database,
        Logger logger
    ) {
        super(database, logger, Tasks.RARE_DATA_FETCHING_TASK.getName());
        mErgastProxy = ergastProxy;
        mDatabase = database;
    }

    @Override
    protected void runTask() throws SQLException {
        fetchRaces();
    }


    private void fetchRaces() throws SQLException {
        final Optional<Integer> nextSeasonToFetch = mDatabase.getNextSeasonToFetchForRaces();
        if (nextSeasonToFetch.isEmpty()) {
            return;
        }

        final List<RaceData> races = mErgastProxy.fetchRacesFromYear(nextSeasonToFetch.get());
        if (!races.isEmpty()) {
            mDatabase.mergeIntoRacesData(races);
        }
    }
}
