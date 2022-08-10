package f1_Info.entry_points.reports.commands.get_race_report_commands.fastest_laps;

import common.constants.CountryCodes;
import lombok.Value;

@Value
public class FastestLapsReportResponse {
    Integer mPosition;
    Integer mDriverNumber;
    String mDriver;
    CountryCodes mCountryCodes;
    String mConstructor;
    Integer mLap;
    String mTime;
    String mAverageSpeed;

    public FastestLapsReportResponse(final FastestLapsRecord fastestLapRecord) {
        mPosition = fastestLapRecord.getPosition();
        mDriverNumber = fastestLapRecord.getDriverNumber();
        mDriver = String.format("%s %s", fastestLapRecord.getFirstName(), fastestLapRecord.getLastName());
        mCountryCodes = CountryCodes.fromCountry(fastestLapRecord.getDriverCountry());
        mConstructor = fastestLapRecord.getConstructor();
        mLap = fastestLapRecord.getLap();
        mTime = fastestLapRecord.getTime();
        mAverageSpeed = formatAverageSpeed(fastestLapRecord.getAverageSpeed());
    }

    private String formatAverageSpeed(final String rawString) {
        if (rawString == null) {
            return null;
        }

        final String[] splits = rawString.split("\\.");
        return String.format("%s.%s", splits[0], splits[1].substring(0, 3));
    }
}
