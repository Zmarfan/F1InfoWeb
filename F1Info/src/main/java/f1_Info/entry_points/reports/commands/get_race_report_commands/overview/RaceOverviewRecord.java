package f1_Info.entry_points.reports.commands.get_race_report_commands.overview;

import common.constants.Country;
import lombok.Value;

import java.time.LocalDate;

@Value
public class RaceOverviewRecord {
    String mDriverIdentifier;
    String mRaceName;
    Country mCountry;
    LocalDate mDate;
    String mWinnerFirstName;
    String mWinnerLastName;
    String mConstructor;
    int mLaps;
    String mTime;
}
