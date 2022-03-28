package f1_Info.background.fetch_drivers_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.DriverData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static f1_Info.database.DatabaseUtils.*;

@Component(value = "FetchDriversTaskDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void mergeIntoDriversData(final List<DriverData> driverDataList) throws SQLException {
        try (final Connection connection = getConnection()) {
            for (final DriverData driverData : driverDataList) {
                try (final PreparedStatement preparedStatement = connection.prepareStatement("""
                    insert into drivers (driver_identifier, number, code, first_name, last_name, date_of_birth, country_code, wikipedia_page)
                       values (?,?,?,?,?,?,?,?) on duplicate key update id = id;
                    """
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
}

