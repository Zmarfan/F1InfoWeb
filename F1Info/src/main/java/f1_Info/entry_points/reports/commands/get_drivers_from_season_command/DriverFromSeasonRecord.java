package f1_Info.entry_points.reports.commands.get_drivers_from_season_command;

import lombok.Value;

@Value
public class DriverFromSeasonRecord {
    String mDriverIdentifier;
    String mFirstName;
    String mLastName;
}
