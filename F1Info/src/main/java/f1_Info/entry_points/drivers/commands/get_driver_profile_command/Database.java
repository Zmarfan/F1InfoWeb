package f1_Info.entry_points.drivers.commands.get_driver_profile_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.drivers.commands.get_all_drivers_command.GetAllDriversQueryData;
import f1_Info.entry_points.shared_data_holders.DriverRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "GetDriverProfileCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public DriverProfileRecord getDriverProfile(final String driverIdentifier) throws SQLException {
        return executeQuery(new GetDriverProfileQueryData(driverIdentifier));
    }
}