package f1_Info.entry_points.drivers.commands.get_all_drivers_command;

import f1_Info.entry_points.shared_data_holders.DriverEntry;
import lombok.Value;

import java.util.List;

@Value
public class DriverProfileFilterResponse {
    List<DriverEntry> mDrivers;
}
