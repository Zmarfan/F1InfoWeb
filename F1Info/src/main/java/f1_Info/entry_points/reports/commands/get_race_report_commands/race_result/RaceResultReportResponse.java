package f1_Info.entry_points.reports.commands.get_race_report_commands.race_result;

import common.constants.CountryCodes;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class RaceResultReportResponse {
    int mPosition;
    int mStartPosition;
    Integer mDriverNumber;
    String mDriver;
    CountryCodes mCountryCodes;
    String mConstructor;
    int mLaps;
    String mTimeRetired;
    BigDecimal mPoints;

    public RaceResultReportResponse(final RaceResultRecord raceRecord) {
        mPosition = raceRecord.getPosition();
        mStartPosition = raceRecord.getStartPosition();
        mDriverNumber = raceRecord.getDriverNumber();
        mDriver = String.format("%s %s", raceRecord.getFirstName(), raceRecord.getLastName());
        mCountryCodes = CountryCodes.fromCountry(raceRecord.getDriverCountry());
        mConstructor = raceRecord.getConstructor();
        mLaps = raceRecord.getLaps();
        mTimeRetired = raceRecord.getTimeRetired();
        mPoints = raceRecord.getPoints();
    }
}
