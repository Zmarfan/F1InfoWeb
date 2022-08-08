package f1_Info.entry_points.reports.commands.get_driver_report_commands.all;

import common.constants.Country;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class AllDriverReportRecord {
    int mPosition;
    String mFirstName;
    String mLastName;
    Country mDriverCountry;
    String mConstructor;
    BigDecimal mPoints;
}
