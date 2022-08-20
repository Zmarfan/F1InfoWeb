package f1_Info.entry_points.drivers.commands.get_driver_chart_info_command.starting;

import database.IQueryData;
import lombok.Value;

@Value
public class GetDriverStartPositionsPerSeasonQueryData implements IQueryData<DriverStartPositionRecord> {
    String mDriverIdentifier;

    @Override
    public String getStoredProcedureName() {
        return "get_driver_chart_info_get_start_positions_per_season";
    }
}
