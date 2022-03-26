package f1_Info.background.rare_data_fetching_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.CircuitData;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.ergast.responses.DriverData;
import f1_Info.ergast.responses.SeasonData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static f1_Info.database.DatabaseUtils.*;

@Component
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void mergeIntoConstructorsData(final List<ConstructorData> constructorDataList) throws SQLException {
        try (final Connection connection = getConnection()) {
            for (final ConstructorData constructorData : constructorDataList) {
                try (final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into constructors (constructor_identifier, name, country_code, wikipedia_page) values (?,?,?,?) on duplicate key update id = id;"
                )) {
                    preparedStatement.setString(1, constructorData.getConstructorIdentifier());
                    preparedStatement.setString(2, constructorData.getName());
                    setCountry(preparedStatement, 3, constructorData.getCountry());
                    setUrl(preparedStatement, 4, constructorData.getWikipediaUrl());
                    preparedStatement.executeUpdate();
                }
            }
        }
    }

    public void mergeIntoDriversData(final List<DriverData> driverDataList) throws SQLException {
        try (final Connection connection = getConnection()) {
            for (final DriverData driverData : driverDataList) {
                try (final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into drivers (driver_identifier, number, code, first_name, last_name, date_of_birth, country_code, wikipedia_page)" +
                        "values (?,?,?,?,?,?,?,?) on duplicate key update id = id;"
                )) {
                    preparedStatement.setString(1, driverData.getDriverIdentifier());
                    setNullableInt(preparedStatement, 2, driverData.getPermanentNumber().orElse(null));
                    setNullableString(preparedStatement, 3, driverData.getCode().orElse(null));
                    preparedStatement.setString(4, driverData.getFirstName());
                    preparedStatement.setString(5, driverData.getLastName());
                    setDate(preparedStatement, 6, driverData.getDateOfBirth());
                    setCountry(preparedStatement, 7, driverData.getCountry());
                    setUrl(preparedStatement, 8, driverData.getWikipediaUrl());
                    preparedStatement.executeUpdate();
                }
            }
        }
    }

    public void mergeIntoSeasonsData(final List<SeasonData> seasonDataList) throws SQLException {
        try (final Connection connection = getConnection()) {
            for (final SeasonData seasonData : seasonDataList) {
                try (final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into seasons (year, wikipedia_page) values (?,?) on duplicate key update year = year;"
                )) {
                    preparedStatement.setInt(1, seasonData.getYear());
                    setUrl(preparedStatement, 2, seasonData.getWikipediaUrl());
                    preparedStatement.executeUpdate();
                }
            }
        }
    }

    public void mergeIntoCircuitsData(final List<CircuitData> circuitDataList) throws SQLException {
        try (final Connection connection = getConnection()) {
            for (final CircuitData circuitData : circuitDataList) {
                try (final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into circuits (circuit_identifier, name, location_name, country_code, latitude, longitude, wikipedia_page)" +
                        "values (?,?,?,?,?,?,?) on duplicate key update id = id;"
                )) {
                    preparedStatement.setString(1, circuitData.getCircuitIdentifier());
                    preparedStatement.setString(2, circuitData.getCircuitName());
                    preparedStatement.setString(3, circuitData.getLocationData().getLocationName());
                    setCountry(preparedStatement, 4, circuitData.getLocationData().getCountry());
                    preparedStatement.setBigDecimal(5, circuitData.getLocationData().getLatitude());
                    preparedStatement.setBigDecimal(6, circuitData.getLocationData().getLongitude());
                    setUrl(preparedStatement, 7, circuitData.getWikipediaUrl());
                    preparedStatement.executeUpdate();
                }
            }
        }
    }
}