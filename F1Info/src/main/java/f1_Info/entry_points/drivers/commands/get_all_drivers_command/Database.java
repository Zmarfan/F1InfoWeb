package f1_Info.entry_points.drivers.commands.get_all_drivers_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.shared_data_holders.DriverRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "GetAllDriversCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public List<DriverRecord> getAllDrivers() throws SQLException {
        return executeListQuery(new GetAllDriversQueryData());
    }
}