package f1_Info.entry_points.reports.commands.get_driver_report_filter_values_command;

import lombok.Value;

@Value
public class RaceEntry {
    String mName;
    int mRound;

    public RaceEntry(final RaceFromSeasonRecord raceRecord) {
        mName = raceRecord.getName();
        mRound = raceRecord.getRound();
    }
}
