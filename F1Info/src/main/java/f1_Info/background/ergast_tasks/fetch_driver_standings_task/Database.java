package f1_Info.background.ergast_tasks.fetch_driver_standings_task;

import f1_Info.background.TaskDatabase;
import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.background.ergast_tasks.ergast.responses.standings.DriverStandingsData;
import f1_Info.background.ergast_tasks.fetch_pitstops_task.SetLastFetchedRacePitStopFetchingQueryData;
import f1_Info.configuration.Configuration;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component(value = "FetchDriverStandingsTaskDatabase")
public class Database extends TaskDatabase {
    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<RaceRecord> getNextRaceToFetchDriverStandingsFor() throws SQLException {
        return Optional.of(new RaceRecord(1950, 1, 1));
        // return executeOptionalQuery(new GetNextRaceToFetchDriverStandingsForQueryData());
    }

    public void mergeIntoPitStopsData(final List<DriverStandingsData> driverStandingsDataList, final RaceRecord raceRecord) throws SQLException {
        // executeBulkOfWork();
    }

    public void setLastFetchedRaceInHistory(final RaceRecord raceRecord) throws SQLException {
        executeVoidQuery(new SetLastFetchedRacePitStopFetchingQueryData(raceRecord));
    }
}
