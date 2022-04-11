package f1_Info.background.ergast_tasks.fetch_races_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.database.BulkOfWork;
import f1_Info.background.ergast_tasks.ergast.responses.race.RaceData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static f1_Info.background.ergast_tasks.ErgastFetchingInformation.FIRST_FORMULA_1_SEASON;

@Component(value = "FetchRacesTaskDatabase")
public class Database extends TaskDatabase {
    private static final int NO_MORE_DATA_CAN_BE_FETCHED = -1;

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<Integer> getNextSeasonToFetchForRaces() throws SQLException {
        final int fetchedSeason = executeBasicQuery(new GetNextSeasonToFetchForRacesQueryData(FIRST_FORMULA_1_SEASON));
        return Optional.of(fetchedSeason).filter(season -> season != NO_MORE_DATA_CAN_BE_FETCHED);
    }

    public void mergeIntoRacesData(final List<RaceData> raceDataList) throws SQLException {
        executeBulkOfWork(new BulkOfWork(raceDataList.stream().map(MergeIntoRacesQueryData::new).toList()));
    }

    public void setLastFetchedSeason(final int lastFetchedSeason) throws SQLException {
        executeVoidQuery(new SetLastFetchedSeasonForRaceFetchingQueryData(lastFetchedSeason));
    }
}
