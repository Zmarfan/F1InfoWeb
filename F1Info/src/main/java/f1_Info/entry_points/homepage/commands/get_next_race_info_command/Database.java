package f1_Info.entry_points.homepage.commands.get_next_race_info_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component(value = "GetNextRaceInfoCommandDatabase}")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public NextRaceInfoRecord getNextRaceInfo() throws SQLException {
        return executeQuery(new GetNextRaceInfoQueryData());
    }
}