package f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.driver;

import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.RaceEntry;
import f1_Info.entry_points.shared_data_holders.DriverEntry;
import lombok.Value;

import java.util.List;

@Value
public class DriverReportFilterResponse {
    List<DriverEntry> mDrivers;
    List<RaceEntry> mRaces;
    boolean mSeasonHasSprints;
}
