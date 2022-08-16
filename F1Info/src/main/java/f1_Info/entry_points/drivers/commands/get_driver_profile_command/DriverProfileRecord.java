package f1_Info.entry_points.drivers.commands.get_driver_profile_command;

import common.constants.Country;
import lombok.Value;

import java.time.LocalDate;

@Value
public class DriverProfileRecord {
    String mWikipediaUrl;
    String mFirstName;
    String mLastName;
    LocalDate mDateOfBirth;
    Country mCountry;
    String mConstructor;
    int mAmountOfChampionships;
    int mAmountOfRunnerUpChampionships;
    String mFirstRaceName;
    String mLastRaceName;
    int mYearsInF1;
    int mRaceStarts;
    int mBestPosition;
    int mAmountOfBestPosition;
    int mBestStartPosition;
    int mAmountOfBestStartPosition;
    int mTeammates;
}
