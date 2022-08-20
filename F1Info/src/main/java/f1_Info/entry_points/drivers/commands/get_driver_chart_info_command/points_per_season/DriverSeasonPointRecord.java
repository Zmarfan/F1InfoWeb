package f1_Info.entry_points.drivers.commands.get_driver_chart_info_command.points_per_season;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class DriverSeasonPointRecord {
    int mYear;
    int mRound;
    BigDecimal mPoints;
}
