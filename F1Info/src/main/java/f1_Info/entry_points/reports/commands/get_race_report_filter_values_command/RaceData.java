package f1_Info.entry_points.reports.commands.get_race_report_filter_values_command;

import lombok.Value;

@Value
public class RaceData {
    String mName;
    int mRound;
    boolean mHasSprint;
    String mCountryIso;

    public RaceData(final RaceInfoRecord circuitRecord) {
        mName = circuitRecord.getName();
        mRound = circuitRecord.getRound();
        mHasSprint = circuitRecord.getHasSprint();
        mCountryIso = circuitRecord.getCountry().getCode();
    }
}
