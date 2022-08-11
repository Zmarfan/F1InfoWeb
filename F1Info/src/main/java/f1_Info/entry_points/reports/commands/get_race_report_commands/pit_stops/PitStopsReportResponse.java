package f1_Info.entry_points.reports.commands.get_race_report_commands.pit_stops;

import common.constants.CountryCodes;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class PitStopsReportResponse {
    int mStopNumber;
    Integer mDriverNumber;
    String mDriver;
    CountryCodes mCountryCodes;
    String mConstructor;
    int mLap;
    String mTime;
    BigDecimal mDuration;

    public PitStopsReportResponse(final PitStopRecord pitStopRecord) {
        mStopNumber = pitStopRecord.getStopNumber();
        mDriverNumber = pitStopRecord.getDriverNumber();
        mDriver = String.format("%s %s", pitStopRecord.getFirstName(), pitStopRecord.getLastName());
        mCountryCodes = CountryCodes.fromCountry(pitStopRecord.getDriverCountry());
        mConstructor = pitStopRecord.getConstructor();
        mLap = pitStopRecord.getLap();
        mTime = pitStopRecord.getTime();
        mDuration = pitStopRecord.getDuration();
    }
}
