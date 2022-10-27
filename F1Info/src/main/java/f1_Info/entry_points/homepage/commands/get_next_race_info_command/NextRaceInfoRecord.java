package f1_Info.entry_points.homepage.commands.get_next_race_info_command;

import common.constants.Country;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Value
public class NextRaceInfoRecord {
    int mRound;
    Country mCountry;
    String mName;
    String mTimeZoneIdentifier;
    LocalDate mFp1Date;
    LocalTime mFp1Time;
    LocalDate mFp2Date;
    LocalTime mFp2Time;
    LocalDate mFp3Date;
    LocalTime mFp3Time;
    LocalDate mSprintDate;
    LocalTime mSprintTime;
    LocalDate mQualifyingDate;
    LocalTime mQualifyingTime;
    LocalDate mRaceDate;
    LocalTime mRaceTime;
}
