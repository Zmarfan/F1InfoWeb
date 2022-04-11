package f1_Info.background.ergast_tasks.fetch_constructor_standings_task;

import f1_Info.background.TaskDatabase;
import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.background.ergast_tasks.ergast.responses.standings.ConstructorStandingsData;
import f1_Info.configuration.Configuration;
import f1_Info.database.BulkOfWork;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static f1_Info.background.ergast_tasks.ErgastFetchingInformation.FIRST_SEASON_WITH_CONSTRUCTOR_STANDINGS_DATA;

@Component(value = "FetchConstructorStandingsTaskDatabase")
public class Database extends TaskDatabase {
    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<RaceRecord> getNextRaceToFetchConstructorStandingsFor() throws SQLException {
        return executeOptionalQuery(new GetNextRaceToFetchConstructorStandingsForQueryData(FIRST_SEASON_WITH_CONSTRUCTOR_STANDINGS_DATA));
    }

    public void mergeIntoConstructorStandingsData(final List<ConstructorStandingsData> constructorStandings, final RaceRecord raceRecord) throws SQLException {
        executeBulkOfWork(new BulkOfWork(
            constructorStandings.stream().map(constructorData -> new MergeIntoConstructorStandingsQueryData(constructorData, raceRecord)).toList()
        ));
    }

    public void setLastFetchedRaceInHistory(final RaceRecord raceRecord) throws SQLException {
        executeVoidQuery(new SetLastFetchedConstructorStandingsFetchingQueryData(raceRecord));
    }
}
