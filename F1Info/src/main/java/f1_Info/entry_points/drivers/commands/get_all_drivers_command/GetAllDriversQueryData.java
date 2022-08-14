package f1_Info.entry_points.drivers.commands.get_all_drivers_command;

import database.IQueryData;
import f1_Info.entry_points.shared_data_holders.DriverRecord;
import lombok.Value;

@Value
public class GetAllDriversQueryData implements IQueryData<DriverRecord> {
    @Override
    public String getStoredProcedureName() {
        return "get_all_drivers";
    }
}
