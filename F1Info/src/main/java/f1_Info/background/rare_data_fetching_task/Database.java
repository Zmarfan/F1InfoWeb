package f1_Info.background.rare_data_fetching_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static f1_Info.database.DatabaseUtils.setCountry;
import static f1_Info.database.DatabaseUtils.setUrl;

@Component(value = "RareDataFetchingTaskDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
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
