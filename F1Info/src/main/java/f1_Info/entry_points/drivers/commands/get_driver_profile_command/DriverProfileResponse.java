package f1_Info.entry_points.drivers.commands.get_driver_profile_command;

import common.constants.CountryCodes;
import lombok.Value;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Value
public class DriverProfileResponse {
    String mWikipediaTitle;
    String mWikipediaUrl;
    String mFullName;
    long mAge;
    String mDateOfBirth;
    CountryCodes mCountryCodes;
    String mConstructor;

    public DriverProfileResponse(final DriverProfileRecord driverRecord) {
        mWikipediaTitle = driverRecord.getWikipediaUrl().substring(driverRecord.getWikipediaUrl().lastIndexOf("/") + 1);
        mWikipediaUrl = driverRecord.getWikipediaUrl();
        mFullName = String.format("%s %s", driverRecord.getFirstName(), driverRecord.getLastName());
        mAge = ChronoUnit.YEARS.between(driverRecord.getDateOfBirth(), LocalDate.now());
        mDateOfBirth = driverRecord.getDateOfBirth().toString();
        mCountryCodes = CountryCodes.fromCountry(driverRecord.getCountry());
        mConstructor = driverRecord.getConstructor() != null ? driverRecord.getConstructor() : "N/A";
    }
}
