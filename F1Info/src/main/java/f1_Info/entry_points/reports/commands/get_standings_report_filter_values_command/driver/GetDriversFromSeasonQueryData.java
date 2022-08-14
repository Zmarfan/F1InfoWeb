package f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.driver;

import database.IQueryData;
import f1_Info.entry_points.shared_data_holders.DriverRecord;
import lombok.Value;

@Value
public class GetDriversFromSeasonQueryData implements IQueryData<DriverRecord> {
    int mSeason;

    @Override
    public String getStoredProcedureName() {
        return "get_driver_report_filter_values_drivers";
    }
}
