package f1_Info.entry_points.drivers.commands.get_driver_chart_info_command.qualifying;

import database.IQueryData;
import lombok.Value;

@Value
public class GetAllDriverStartPositionsQueryData implements IQueryData<Integer> {
    String mDriverIdentifier;

    @Override
    public String getStoredProcedureName() {
        return "get_driver_chart_info_get_all_start_positions";
    }
}
