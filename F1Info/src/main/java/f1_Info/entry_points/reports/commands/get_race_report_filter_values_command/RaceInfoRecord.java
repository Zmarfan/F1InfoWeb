package f1_Info.entry_points.reports.commands.get_race_report_filter_values_command;

import common.constants.Country;
import lombok.Value;

@Value
public class RaceInfoRecord {
    String mName;
    int mRound;
    Country mCountry;
}
