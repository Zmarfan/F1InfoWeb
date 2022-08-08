package f1_Info.entry_points.reports.commands.get_driver_report_commands.individual;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class IndividualDriverReportResponse {
    String mCircuitName;
    String mCircuitIsoCode;
    String mDate;
    String mConstructor;
    String mRacePosition;
    BigDecimal mPoints;

    public IndividualDriverReportResponse(final IndividualDriverReportRecord reportRecord) {
        mCircuitName = reportRecord.getCircuitName();
        mCircuitIsoCode = reportRecord.getCircuitCountry().getCode();
        mDate = reportRecord.getDate().toLocalDate().toString();
        mConstructor = reportRecord.getConstructor();
        mRacePosition = reportRecord.getRacePosition();
        mPoints = reportRecord.getPoints();
    }
}
