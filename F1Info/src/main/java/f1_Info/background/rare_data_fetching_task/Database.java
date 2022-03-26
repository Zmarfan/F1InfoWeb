package f1_Info.background.rare_data_fetching_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.ergast.responses.DriverData;
import f1_Info.ergast.responses.SeasonData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

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
                    preparedStatement.setString(3, constructorData.getCountry().getCode());
                    preparedStatement.setString(4, constructorData.getWikipediaUrl());
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
                    if (driverData.getPermanentNumber().isPresent()) {
                        preparedStatement.setInt(2, driverData.getPermanentNumber().get());
                    } else {
                        preparedStatement.setNull(2, Types.INTEGER);
                    }
                    if (driverData.getCode().isPresent()) {
                        preparedStatement.setString(3, driverData.getCode().get());
                    } else {
                        preparedStatement.setNull(3, Types.VARCHAR);
                    }
                    preparedStatement.setString(4, driverData.getFirstName());
                    preparedStatement.setString(5, driverData.getLastName());
                    preparedStatement.setDate(6, new Date(driverData.getDateOfBirth().getTime()));
                    preparedStatement.setString(7, driverData.getCountry().getCode());
                    preparedStatement.setString(8, driverData.getWikipediaUrl());
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
                    preparedStatement.setString(2, seasonData.getWikipediaUrl());
                    preparedStatement.executeUpdate();
                }
            }
        }
    }
}
