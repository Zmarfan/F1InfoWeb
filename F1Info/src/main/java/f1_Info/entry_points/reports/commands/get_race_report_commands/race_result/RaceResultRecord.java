package f1_Info.entry_points.reports.commands.get_race_report_commands.race_result;

import common.constants.Country;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class RaceResultRecord {
    int mPosition;
    Integer mDriverNumber;
    String mFirstName;
    String mLastName;
    Country mDriverCountry;
    String mConstructor;
    int mLaps;
    String mTimeRetired;
    BigDecimal mPoints;
}
