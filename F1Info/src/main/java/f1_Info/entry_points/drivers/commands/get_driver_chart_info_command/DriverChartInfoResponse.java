package f1_Info.entry_points.drivers.commands.get_driver_chart_info_command;

import f1_Info.entry_points.drivers.commands.get_driver_chart_info_command.qualifying.StartPosition;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Value
public class DriverChartInfoResponse {
    Map<Integer, List<BigDecimal>> mPointsPerSeasons;
    List<StartPosition> mStartPositions;
}
