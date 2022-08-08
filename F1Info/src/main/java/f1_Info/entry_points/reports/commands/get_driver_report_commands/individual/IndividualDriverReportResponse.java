package f1_Info.entry_points.reports.commands.get_driver_report_commands.individual;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class IndividualDriverReportResponse {
    String mGrandPrix;
    String mDate;
    String mConstructor;
    int mRacePosition;
    BigDecimal mPoints;
}
