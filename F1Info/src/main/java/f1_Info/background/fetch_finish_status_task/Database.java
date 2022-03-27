package f1_Info.background.fetch_finish_status_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.FinishStatusData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component(value = "FetchFinishStatusTaskDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void mergeIntoFinishStatusData(final List<FinishStatusData> finishStatusDataList) throws SQLException {
        try (final Connection connection = getConnection()) {
            for (final FinishStatusData finishStatusData : finishStatusDataList) {
                try (final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into finish_status (id, type) values (?,?) on duplicate key update id = id;"
                )) {
                    preparedStatement.setInt(1, finishStatusData.getId());
                    preparedStatement.setString(2, finishStatusData.getStatus());
                    preparedStatement.executeUpdate();
                }
            }
        }
    }
}