package f1_Info.background.on_demand_data_fetching_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.race.RaceData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static f1_Info.database.DatabaseUtils.*;

@Component(value = "OnDemandDataFetchingTask")
public class Database extends TaskDatabase {
    private static final int FIRST_SEASON_IN_FORMULA_1 = 1950;

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<Integer> getNextSeasonToFetchForRaces() throws SQLException {
        try (final Connection connection = getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement(
                "select max(seasons.year), max(races.year) from seasons left join races on races.year = seasons.year;"
            )) {
                final ResultSet resultSet = preparedStatement.executeQuery();

                resultSet.next();
                final int readSeasonYear = resultSet.getInt(1);
                final Optional<Integer> seasonYear = resultSet.wasNull() ? Optional.empty() : Optional.of(readSeasonYear);
                final int readRacesYear = resultSet.getInt(2);
                final Optional<Integer> racesYear = resultSet.wasNull() ? Optional.empty() : Optional.of(readRacesYear);

                if (seasonYear.isEmpty() || (racesYear.isPresent() && seasonYear.get().equals(racesYear.get()))) {
                    return Optional.empty();
                }
                return Optional.of(racesYear.isEmpty() ? FIRST_SEASON_IN_FORMULA_1 : racesYear.get() + 1);
            }
        }
    }

    public void mergeIntoRacesData(final List<RaceData> raceDataList) throws SQLException {
        try (final Connection connection = getConnection()) {
            for (final RaceData raceData : raceDataList) {
                try (final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into races(" +
                            "circuit_id," +
                            "year," +
                            "round," +
                            "name," +
                            "race_time_start," +
                            "race_date," +
                            "qualifying_date," +
                            "sprint_date," +
                            "first_practice_date," +
                            "second_practice_date," +
                            "third_practice_date," +
                            "wikipedia_page" +
                        ") values ((select id from circuits where circuit_identifier = ?),?,?,?,?,?,?,?,?,?,?,?);"
                )) {
                    preparedStatement.setString(1, raceData.getCircuitData().getCircuitIdentifier());
                    preparedStatement.setInt(2, raceData.getYear());
                    preparedStatement.setInt(3, raceData.getRound());
                    preparedStatement.setString(4, raceData.getRaceName());
                    setInstant(preparedStatement, 5, raceData.getRaceTime());
                    setDate(preparedStatement, 6, raceData.getRaceDate());
                    setDate(preparedStatement, 7, raceData.getQualifyingDate());
                    setDate(preparedStatement, 8, raceData.getSprintDate());
                    setDate(preparedStatement, 9, raceData.getFirstPracticeDate());
                    setDate(preparedStatement, 10, raceData.getSecondPracticeDate());
                    setDate(preparedStatement, 11, raceData.getThirdPracticeDate());
                    setUrl(preparedStatement, 12, raceData.getWikipediaUrl());

                    preparedStatement.executeUpdate();
                }
            }
        }
    }
}
