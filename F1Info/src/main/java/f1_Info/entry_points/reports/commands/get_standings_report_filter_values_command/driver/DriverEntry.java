package f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.driver;

import lombok.Value;

@Value
public class DriverEntry {
    String mDriverIdentifier;
    String mFullName;

    public DriverEntry(final DriverFromSeasonRecord driverRecord) {
        mDriverIdentifier = driverRecord.getDriverIdentifier();
        mFullName = String.format("%s %s", driverRecord.getFirstName(), driverRecord.getLastName());
    }
}
