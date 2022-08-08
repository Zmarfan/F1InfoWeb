package f1_Info.entry_points.reports.commands.get_all_driver_report_command;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class AllDriverReportResponse {
    int mPosition;
    String mDriverFullName;
    String mDriverCountryIOC;
    String mConstructor;
    BigDecimal mPoints;

    public AllDriverReportResponse(final AllDriverReportRecord reportRecord) {
        mPosition = reportRecord.getPosition();
        mDriverFullName = String.format("%s %s", reportRecord.getFirstName(), reportRecord.getLastName());
        mDriverCountryIOC = reportRecord.getDriverCountry().getIOCCode();
        mConstructor = reportRecord.getConstructor();
        mPoints = reportRecord.getPoints();
    }
}
