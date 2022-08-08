package f1_Info.entry_points.reports.commands.get_drivers_from_season_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetDriversFromSeasonQueryData implements IQueryData<DriverFromSeasonRecord> {
    int mSeason;

    @Override
    public String getStoredProcedureName() {
        return "get_drivers_from_season_get";
    }
}
