package f1_Info.entry_points.reports.commands.get_race_report_commands.qualifying;

import common.constants.Country;
import lombok.Value;

@Value
public class QualifyingRecord {
    int mPosition;
    Integer mDriverNumber;
    String mFirstName;
    String mLastName;
    Country mDriverCountry;
    String mConstructor;
    String mQ1;
    String mQ2;
    String mQ3;
}
