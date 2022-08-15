package f1_Info.entry_points.reports.commands.get_race_report_commands.overview;

import lombok.Value;

@Value
public class OverviewRaceReportResponse {
    String mDriverIdentifier;
    String mRaceName;
    String mRaceIsoCode;
    String mDate;
    String mWinner;
    String mConstructor;
    int mLaps;
    String mTime;

    public OverviewRaceReportResponse(final RaceOverviewRecord overviewRecord) {
        mDriverIdentifier = overviewRecord.getDriverIdentifier();
        mRaceName = overviewRecord.getRaceName();
        mRaceIsoCode = overviewRecord.getCountry().getCode();
        mDate = overviewRecord.getDate().toString();
        mWinner = String.format("%s %s", overviewRecord.getWinnerFirstName(), overviewRecord.getWinnerLastName());
        mConstructor = overviewRecord.getConstructor();
        mLaps = overviewRecord.getLaps();
        mTime = overviewRecord.getTime();
    }
}
