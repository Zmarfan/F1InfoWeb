package f1_Info.entry_points.drivers.commands.get_driver_profile_command;

import lombok.Value;

@Value
public class DriverProfileResponse {
    String mWikipediaTitle;
    String mWikipediaUrl;

    public DriverProfileResponse(final DriverProfileRecord driverRecord) {
        mWikipediaTitle = driverRecord.getWikipediaUrl().substring(driverRecord.getWikipediaUrl().lastIndexOf("/") + 1);
        mWikipediaUrl = driverRecord.getWikipediaUrl();
    }
}
