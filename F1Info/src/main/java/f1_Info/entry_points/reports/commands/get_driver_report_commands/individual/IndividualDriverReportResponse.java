package f1_Info.entry_points.reports.commands.get_driver_report_commands.individual;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class IndividualDriverReportResponse {
    String mRaceName;
    String mRaceIsoCode;
    String mDate;
    String mConstructor;
    String mRacePosition;
    BigDecimal mPoints;

    public IndividualDriverReportResponse(final IndividualDriverReportRecord reportRecord) {
        mRaceName = reportRecord.getRaceName();
        mRaceIsoCode = reportRecord.getRaceCountry().getCode();
        mDate = reportRecord.getDate().toLocalDate().toString();
        mConstructor = reportRecord.getConstructor();
        mRacePosition = reportRecord.getRacePosition();
        mPoints = reportRecord.getPoints();
    }
}
