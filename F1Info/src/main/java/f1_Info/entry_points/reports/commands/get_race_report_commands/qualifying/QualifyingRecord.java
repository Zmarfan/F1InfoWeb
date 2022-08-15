package f1_Info.entry_points.reports.commands.get_race_report_commands.qualifying;

import common.constants.Country;
import lombok.Value;

@Value
public class QualifyingRecord {
    String mDriverIdentifier;
    int mPosition;
    Integer mDriverNumber;
    String mFirstName;
    String mLastName;
    Country mDriverCountry;
    String mConstructor;
    String mQ1Diff;
    String mQ1Time;
    String mQ2Diff;
    String mQ2Time;
    String mQ3Diff;
    String mQ3Time;
}
