package f1_Info.entry_points.drivers;

import f1_Info.entry_points.drivers.commands.get_all_drivers_command.Database;
import f1_Info.entry_points.drivers.commands.get_all_drivers_command.GetAllDriversCommand;
import f1_Info.entry_points.drivers.commands.get_driver_profile_command.GetDriverProfileCommand;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Drivers")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class DriversEndpoint {
    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;
    private final Database mDatabase;
    private final f1_Info.entry_points.drivers.commands.get_driver_profile_command.Database mGetDriverProfileDatabase;

    @GetMapping("/all-drivers")
    public ResponseEntity<?> getAllDrivers() {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new GetAllDriversCommand(mDatabase));
    }

    @GetMapping("/driver-profile/{driverIdentifier}")
    public ResponseEntity<?> getDriverProfile(
        @PathVariable("driverIdentifier") final String driverIdentifier
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (driverIdentifier == null || driverIdentifier.isEmpty()) {
                throw new BadRequestException();
            }

            return new GetDriverProfileCommand(driverIdentifier, mGetDriverProfileDatabase);
        });
    }
}
