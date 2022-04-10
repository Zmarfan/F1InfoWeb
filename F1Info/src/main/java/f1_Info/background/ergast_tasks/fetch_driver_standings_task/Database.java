package f1_Info.background.ergast_tasks.fetch_driver_standings_task;

import f1_Info.background.TaskDatabase;
import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.background.ergast_tasks.ergast.responses.standings.DriverStandingsData;
import f1_Info.configuration.Configuration;
import f1_Info.database.BulkOfWork;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component(value = "FetchDriverStandingsTaskDatabase")
public class Database extends TaskDatabase {
    private static final int FIRST_FORMULA_1_SEASON = 1950;

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<RaceRecord> getNextRaceToFetchDriverStandingsFor() throws SQLException {
        return executeOptionalQuery(new GetNextRaceToFetchDriverStandingsForQueryData(FIRST_FORMULA_1_SEASON));
    }

    public void mergeIntoDriverStandingsData(final List<DriverStandingsData> driverStandings, final RaceRecord raceRecord) throws SQLException {
        executeBulkOfWork(
            new BulkOfWork(driverStandings.stream().map(driverStanding -> new MergeIntoDriverStandingsQueryData(driverStanding, raceRecord)).toList())
        );
    }

    public void setLastFetchedRaceInHistory(final RaceRecord raceRecord) throws SQLException {
        executeVoidQuery(new SetLastFetchedDriverStandingsFetchingQueryData(raceRecord));
    }
}
