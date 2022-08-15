package f1_Info.entry_points.reports.commands.get_driver_report_commands.all;

import common.constants.CountryCodes;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class AllDriverReportResponse {
    String mDriverIdentifier;
    int mPosition;
    PositionMove mPositionMove;
    Integer mDriverNumber;
    String mDriverFullName;
    CountryCodes mCountryCodes;
    String mConstructor;
    BigDecimal mPoints;

    public AllDriverReportResponse(final AllDriverReportRecord reportRecord) {
        mDriverIdentifier = reportRecord.getDriverIdentifier();
        mPosition = reportRecord.getPosition();
        mPositionMove = reportRecord.getPositionMove();
        mDriverNumber = reportRecord.getDriverNumber();
        mDriverFullName = String.format("%s %s", reportRecord.getFirstName(), reportRecord.getLastName());
        mCountryCodes = CountryCodes.fromCountry(reportRecord.getDriverCountry());
        mConstructor = reportRecord.getConstructor();
        mPoints = reportRecord.getPoints();
    }
}
