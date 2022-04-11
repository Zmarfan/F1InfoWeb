package f1_Info.background.ergast_tasks.fetch_constructor_standings_task;

import f1_Info.background.TaskDatabase;
import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.background.ergast_tasks.ergast.responses.standings.ConstructorStandingsData;
import f1_Info.configuration.Configuration;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component(value = "FetchConstructorStandingsTaskDatabase")
public class Database extends TaskDatabase {
    private static final int FIRST_SEASON_IN_FORMULA_1 = 1950;

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<RaceRecord> getNextRaceToFetchConstructorStandingsFor() throws SQLException {
        return Optional.of(new RaceRecord(1990, 1, 1));
    }

    public void mergeIntoConstructorStandingsData(final List<ConstructorStandingsData> constructorStandings, final RaceRecord raceRecord) throws SQLException {
    }

    public void setLastFetchedRaceInHistory(final RaceRecord raceRecord) throws SQLException {
    }
}
