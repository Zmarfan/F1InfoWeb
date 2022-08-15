package f1_Info.entry_points.drivers.commands.get_driver_profile_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetDriverProfileQueryData implements IQueryData<DriverProfileRecord> {
    String mDriverIdentifier;

    @Override
    public String getStoredProcedureName() {
        return "get_driver_profile_get";
    }
}
