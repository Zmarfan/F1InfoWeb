package f1_Info.entry_points.reports.commands.get_race_report_commands.fastest_laps;

import common.constants.Country;
import lombok.Value;

@Value
public class FastestLapsRecord {
    String mDriverIdentifier;
    Integer mPosition;
    Integer mDriverNumber;
    String mFirstName;
    String mLastName;
    Country mDriverCountry;
    String mConstructor;
    Integer mLap;
    String mTime;
    String mAverageSpeed;
}
