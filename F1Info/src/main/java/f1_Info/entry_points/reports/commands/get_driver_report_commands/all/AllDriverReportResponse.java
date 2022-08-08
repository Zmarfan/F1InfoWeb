package f1_Info.entry_points.reports.commands.get_driver_report_commands.all;

import common.constants.CountryCodes;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class AllDriverReportResponse {
    int mPosition;
    String mDriverFullName;
    CountryCodes mCountryCodes;
    String mConstructor;
    BigDecimal mPoints;

    public AllDriverReportResponse(final AllDriverReportRecord reportRecord) {
        mPosition = reportRecord.getPosition();
        mDriverFullName = String.format("%s %s", reportRecord.getFirstName(), reportRecord.getLastName());
        mCountryCodes = CountryCodes.fromCountry(reportRecord.getDriverCountry());
        mConstructor = reportRecord.getConstructor();
        mPoints = reportRecord.getPoints();
    }
}
