package f1_Info.background.fetch_races_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.database.DatabaseBulkOfWork;
import f1_Info.ergast.responses.race.RaceData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component(value = "FetchRacesTaskDatabase")
public class Database extends TaskDatabase {
    private static final int FIRST_FORMULA_ONE_SEASON = 1950;
    private static final int NO_MORE_DATA_CAN_BE_FETCHED = -1;

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<Integer> getNextSeasonToFetchForRaces() throws SQLException {
        final Integer fetchedSeason = executeBasicQuery(new GetNextSeasonToFetchForRacesQueryData(FIRST_FORMULA_ONE_SEASON));
        return Optional.ofNullable(fetchedSeason).filter(season -> season != NO_MORE_DATA_CAN_BE_FETCHED);
    }

    public void mergeIntoRacesData(final List<RaceData> raceDataList) throws SQLException {
        final DatabaseBulkOfWork bulkOfWork = new DatabaseBulkOfWork();
        bulkOfWork.add(raceDataList.stream().map(MergeIntoRacesQueryData::new).toList());
        executeBulkOfWork(bulkOfWork);
    }

    public void setLastFetchedSeason(final int lastFetchedSeason) throws SQLException {
        executeVoidQuery(new SetLastFetchedSeasonForRaceFetchingQueryData(lastFetchedSeason));
    }
}
