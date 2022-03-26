package f1_Info.background.on_demand_data_fetching_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component(value = "OnDemandDataFetchingTask")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<Integer> getLatestFetchedSeasonInDb() throws SQLException {
        try (final Connection connection = getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement("select max(year) from seasons;")) {
                final ResultSet resultSet = preparedStatement.executeQuery();

                resultSet.next();
                final int year = resultSet.getInt(1);
                return resultSet.wasNull() ? Optional.empty() : Optional.of(year);
            }
        }
    }
}
