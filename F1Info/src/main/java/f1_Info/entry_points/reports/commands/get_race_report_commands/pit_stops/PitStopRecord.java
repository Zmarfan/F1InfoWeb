package f1_Info.entry_points.reports.commands.get_race_report_commands.pit_stops;

import common.constants.Country;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class PitStopRecord {
    int mStopNumber;
    Integer mDriverNumber;
    String mFirstName;
    String mLastName;
    Country mDriverCountry;
    String mConstructor;
    int mLap;
    String mTime;
    BigDecimal mDuration;
}
