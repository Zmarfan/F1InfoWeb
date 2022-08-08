package f1_Info.entry_points.reports.commands.get_all_driver_report_command;

import common.constants.Country;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class AllDriverReportResponse {
    int mPosition;
    String mDriverFullName;
    Country mDriverCountry;
    String mConstructor;
    BigDecimal mPoints;
}
