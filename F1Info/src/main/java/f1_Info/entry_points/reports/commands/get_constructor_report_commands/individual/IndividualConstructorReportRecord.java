package f1_Info.entry_points.reports.commands.get_constructor_report_commands.individual;

import common.constants.Country;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class IndividualConstructorReportRecord {
    String mRaceName;
    Country mRaceCountry;
    LocalDateTime mDate;
    BigDecimal mPoints;
}
