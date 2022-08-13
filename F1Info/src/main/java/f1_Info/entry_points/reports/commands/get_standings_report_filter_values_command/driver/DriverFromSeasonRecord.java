package f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.driver;

import lombok.Value;

@Value
public class DriverFromSeasonRecord {
    String mDriverIdentifier;
    String mFirstName;
    String mLastName;
}
