package f1_Info.entry_points.drivers.commands.get_driver_profile_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetDriverProfileCommand implements Command {
    private final String mDriverIdentifier;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final DriverProfileRecord driverProfileRecord = mDatabase.getDriverProfile(mDriverIdentifier);
        return ok(new DriverProfileResponse(driverProfileRecord));
    }
}
