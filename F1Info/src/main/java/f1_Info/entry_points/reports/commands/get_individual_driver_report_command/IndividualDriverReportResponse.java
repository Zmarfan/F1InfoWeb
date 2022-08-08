package f1_Info.entry_points.reports.commands.get_individual_driver_report_command;

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
