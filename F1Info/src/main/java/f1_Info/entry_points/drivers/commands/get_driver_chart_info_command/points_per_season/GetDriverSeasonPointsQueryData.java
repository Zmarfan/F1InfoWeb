package f1_Info.entry_points.drivers.commands.get_driver_chart_info_command.points_per_season;

import database.IQueryData;
import lombok.Value;

@Value
public class GetDriverSeasonPointsQueryData implements IQueryData<DriverSeasonPointRecord> {
    String mDriverIdentifier;

    @Override
    public String getStoredProcedureName() {
        return "get_driver_chart_info_get_season_points";
    }
}
