package f1_Info.entry_points.reports.commands.get_driver_report_commands.individual;

import common.constants.Country;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class IndividualDriverReportRecord {
    String mRaceName;
    Country mRaceCountry;
    LocalDateTime mDate;
    String mConstructor;
    String mRacePosition;
    BigDecimal mPoints;
}
