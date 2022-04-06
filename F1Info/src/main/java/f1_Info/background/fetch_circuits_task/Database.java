package f1_Info.background.fetch_circuits_task;

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

import static f1_Info.database.StatementHelper.setString;

@Component(value = "FetchCircuitsTaskDatabase")
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
                if (circuitDataAlreadyExistInDatabase(circuitData, connection)) {
                    insertCircuitDataEntry(circuitData, connection);
                }
            }
        }
    }

    private boolean circuitDataAlreadyExistInDatabase(final CircuitData circuitData, final Connection connection) throws SQLException {
        try (final PreparedStatement preparedStatement = connection.prepareStatement("select id from circuits where circuit_identifier = ?;")) {
            preparedStatement.setString(1, circuitData.getCircuitIdentifier());
            return preparedStatement.executeQuery().next();
        }
    }

    private void insertCircuitDataEntry(final CircuitData circuitData, final Connection connection) throws SQLException {
        try (final PreparedStatement preparedStatement = connection.prepareStatement("""
           insert into circuits (circuit_identifier, name, location_name, country_code, latitude, longitude, wikipedia_page) values (?,?,?,?,?,?,?);
           """
        )) {
            preparedStatement.setString(1, circuitData.getCircuitIdentifier());
            preparedStatement.setString(2, circuitData.getCircuitName());
            preparedStatement.setString(3, circuitData.getLocationData().getLocationName());
            setString(preparedStatement, 4, circuitData.getLocationData().getCountry().getCode());
            preparedStatement.setBigDecimal(5, circuitData.getLocationData().getLatitude());
            preparedStatement.setBigDecimal(6, circuitData.getLocationData().getLongitude());
            setString(preparedStatement, 7, circuitData.getWikipediaUrl().getUrl());
            preparedStatement.executeUpdate();
        }
    }
}
