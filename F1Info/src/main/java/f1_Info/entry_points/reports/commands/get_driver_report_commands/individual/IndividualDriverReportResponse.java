package f1_Info.entry_points.reports.commands.get_driver_report_commands.individual;

import common.constants.CountryCodes;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class IndividualDriverReportResponse {
    String mCircuitName;
    CountryCodes mCircuitCountryCodes;
    String mDate;
    String mConstructor;
    String mRacePosition;
    BigDecimal mPoints;

    public IndividualDriverReportResponse(final IndividualDriverReportRecord reportRecord) {
        mCircuitName = reportRecord.getCircuitName();
        mCircuitCountryCodes = CountryCodes.fromCountry(reportRecord.getCircuitCountry());
        mDate = reportRecord.getDate().toLocalDate().toString();
        mConstructor = reportRecord.getConstructor();
        mRacePosition = reportRecord.getRacePosition();
        mPoints = reportRecord.getPoints();
    }
}
