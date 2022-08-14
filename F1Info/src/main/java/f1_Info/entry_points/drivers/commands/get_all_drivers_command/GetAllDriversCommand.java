package f1_Info.entry_points.drivers.commands.get_all_drivers_command;

import f1_Info.entry_points.helper.Command;
import f1_Info.entry_points.shared_data_holders.DriverEntry;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetAllDriversCommand implements Command {
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final List<DriverEntry> drivers = mDatabase.getAllDrivers().stream().map(DriverEntry::new).toList();
        return ok(new DriverProfileFilterResponse(drivers));
    }
}
