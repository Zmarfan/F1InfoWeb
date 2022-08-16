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
    int mAmountOfChampionships;
    int mAmountOfRunnerUpChampionships;
    String mFirstRaceName;
    String mLastRaceName;
    int mYearsInF1;
    int mRaceStarts;
    String mHighestFinish;
    String mHighestStartFinish;
    int mTeammates;

    public DriverProfileResponse(final DriverProfileRecord driverRecord) {
        mWikipediaTitle = driverRecord.getWikipediaUrl().substring(driverRecord.getWikipediaUrl().lastIndexOf("/") + 1);
        mWikipediaUrl = driverRecord.getWikipediaUrl();
        mFullName = String.format("%s %s", driverRecord.getFirstName(), driverRecord.getLastName());
        mAge = ChronoUnit.YEARS.between(driverRecord.getDateOfBirth(), LocalDate.now());
        mDateOfBirth = driverRecord.getDateOfBirth().toString();
        mCountryCodes = CountryCodes.fromCountry(driverRecord.getCountry());
        mConstructor = driverRecord.getConstructor() != null ? driverRecord.getConstructor() : "N/A";
        mAmountOfChampionships = driverRecord.getAmountOfChampionships();
        mAmountOfRunnerUpChampionships = driverRecord.getAmountOfRunnerUpChampionships();
        mFirstRaceName = driverRecord.getFirstRaceName();
        mLastRaceName = driverRecord.getLastRaceName();
        mYearsInF1 = driverRecord.getYearsInF1();
        mRaceStarts = driverRecord.getRaceStarts();
        mHighestFinish = valueWithAmount(driverRecord.getBestPosition(), driverRecord.getAmountOfBestPosition());
        mHighestStartFinish = valueWithAmount(driverRecord.getBestStartPosition(), driverRecord.getAmountOfBestStartPosition());
        mTeammates = driverRecord.getTeammates();
    }

    private String valueWithAmount(final int value, final int amount) {
        return String.format("%d (x%d)", value, amount);
    }
}
