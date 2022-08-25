package f1_Info.entry_points.change_log.commands.get_change_log_items_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "GetChangeLogItemsCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public List<ChangeLogItemRecord> getChangeLogItems() throws SQLException {
        return executeListQuery(new GetChangeLogItemsQueryData());
    }
}