package f1_Info.background.fetch_races_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.race.RaceData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static f1_Info.database.DatabaseUtils.*;

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
        try (final Connection connection = getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement("""
                select ifnull((select
                  case when seasons.year is not null then history.season + 1 else -1 end as next_season_to_fetch
                from
                  races_fetching_history history
                    left join seasons on seasons.year = history.season + 1
                where
                    is_active = 'Y'), ?) as next_fetch_year;
            """)) {
                preparedStatement.setInt(1, FIRST_FORMULA_ONE_SEASON);
                final ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return readNullableInt(resultSet, 1).filter(nextSeason -> nextSeason != NO_MORE_DATA_CAN_BE_FETCHED);
            }
        }
    }

    public void mergeIntoRacesData(final List<RaceData> raceDataList) throws SQLException {
        try (final Connection connection = getConnection()) {
            for (final RaceData raceData : raceDataList) {
                if (!raceDataAlreadyExistInDatabase(raceData, connection)) {
                    insertRaceDataEntry(raceData, connection);
                }
            }
        }
    }

    public void setLastFetchedSeason(final int lastFetchedSeason) throws SQLException {
        try (final Connection connection = getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement("""
                update races_fetching_history set is_active = 'N' where is_active = 'Y';
            """)) {
                preparedStatement.executeUpdate();
            }
            try (final PreparedStatement preparedStatement = connection.prepareStatement("""
                insert into races_fetching_history (season, is_active) values (?,'Y');
            """)) {
                preparedStatement.setInt(1, lastFetchedSeason);
                preparedStatement.executeUpdate();
            }
        }
    }

    private boolean raceDataAlreadyExistInDatabase(final RaceData raceData, final Connection connection) throws SQLException {
        try (final PreparedStatement preparedStatement = connection.prepareStatement("select id from races where year = ? and round = ?;")) {
            preparedStatement.setInt(1, raceData.getYear());
            preparedStatement.setInt(2, raceData.getRound());
            return preparedStatement.executeQuery().next();
        }
    }

    private void insertRaceDataEntry(final RaceData raceData, final Connection connection) throws SQLException {
        final Optional<Integer> raceTimeAndDateId = insertTimeAndDate(raceData.getRaceTime(), raceData.getRaceDate(), connection);
        final Optional<Integer> qualifyingTimeAndDateId = insertTimeAndDate(raceData.getQualifyingTime(), raceData.getQualifyingDate(), connection);
        final Optional<Integer> sprintTimeAndDateId = insertTimeAndDate(raceData.getSprintTime(), raceData.getSprintDate(), connection);
        final Optional<Integer> fp1TimeAndDateId = insertTimeAndDate(raceData.getFirstPracticeTime(), raceData.getFirstPracticeDate(), connection);
        final Optional<Integer> fp2TimeAndDateId = insertTimeAndDate(raceData.getSecondPracticeTime(), raceData.getSecondPracticeDate(), connection);
        final Optional<Integer> fp3TimeAndDateId = insertTimeAndDate(raceData.getThirdPracticeTime(), raceData.getThirdPracticeDate(), connection);

        try (final PreparedStatement preparedStatement = connection.prepareStatement("""
                    insert into races(
                        circuit_id,
                        year,
                        round,
                        name,
                        race_time_and_date_id,
                        qualifying_time_and_date_id,
                        sprint_time_and_date_id,
                        first_practice_time_and_date_id,
                        second_practice_time_and_date_id,
                        third_practice_time_and_date_id,
                        wikipedia_page
                    ) values ((select id from circuits where circuit_identifier = ?),?,?,?,?,?,?,?,?,?,?) on duplicate key update id = id;
                    """
        )) {
            preparedStatement.setString(1, raceData.getCircuitData().getCircuitIdentifier());
            preparedStatement.setInt(2, raceData.getYear());
            preparedStatement.setInt(3, raceData.getRound());
            preparedStatement.setString(4, raceData.getRaceName());
            setNullableInt(preparedStatement, 5, raceTimeAndDateId.orElse(null));
            setNullableInt(preparedStatement, 6, qualifyingTimeAndDateId.orElse(null));
            setNullableInt(preparedStatement, 7, sprintTimeAndDateId.orElse(null));
            setNullableInt(preparedStatement, 8, fp1TimeAndDateId.orElse(null));
            setNullableInt(preparedStatement, 9, fp2TimeAndDateId.orElse(null));
            setNullableInt(preparedStatement, 10, fp3TimeAndDateId.orElse(null));
            setUrl(preparedStatement, 11, raceData.getWikipediaUrl());

            preparedStatement.executeUpdate();
        }
    }

    private Optional<Integer> insertTimeAndDate(final Time time, final java.util.Date date, final Connection connection) throws SQLException {
        if (time == null && date == null) {
            return Optional.empty();
        }

        try (final PreparedStatement preparedStatement = connection.prepareStatement(
            "insert into time_and_dates (date, time) values (?,?);",
            Statement.RETURN_GENERATED_KEYS
        )) {
            setDate(preparedStatement, 1, date);
            setTime(preparedStatement, 2, time);
            preparedStatement.executeUpdate();
            final ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return Optional.of(resultSet.getInt(1));
            }
            throw new SQLException("Unable to read and return corresponding id created from background job insert");
        }
    }
}
