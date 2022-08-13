package f1_Info.entry_points.reports.commands.get_constructor_report_commands.individual;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class IndividualConstructorReportResponse {
    String mRaceName;
    String mRaceIsoCode;
    String mDate;
    BigDecimal mPoints;

    public IndividualConstructorReportResponse(final IndividualConstructorReportRecord reportRecord) {
        mRaceName = reportRecord.getRaceName();
        mRaceIsoCode = reportRecord.getRaceCountry().getCode();
        mDate = reportRecord.getDate().toLocalDate().toString();
        mPoints = reportRecord.getPoints();
    }
}
