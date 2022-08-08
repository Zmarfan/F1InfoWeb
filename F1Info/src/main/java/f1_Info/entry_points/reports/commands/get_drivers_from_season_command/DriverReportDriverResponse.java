package f1_Info.entry_points.reports.commands.get_drivers_from_season_command;

import lombok.Value;

@Value
public class DriverReportDriverResponse {
    String mDriverIdentifier;
    String mFullName;

    public DriverReportDriverResponse(final DriverFromSeasonRecord driverRecord) {
        mDriverIdentifier = driverRecord.getDriverIdentifier();
        mFullName = String.format("%s %s", driverRecord.getFirstName(), driverRecord.getLastName());
    }
}
