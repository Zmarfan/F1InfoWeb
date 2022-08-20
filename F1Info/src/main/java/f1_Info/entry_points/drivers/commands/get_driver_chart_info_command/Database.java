package f1_Info.entry_points.drivers.commands.get_driver_chart_info_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.drivers.commands.get_driver_chart_info_command.points_per_season.DriverSeasonPointRecord;
import f1_Info.entry_points.drivers.commands.get_driver_chart_info_command.points_per_season.GetDriverSeasonPointsQueryData;
import f1_Info.entry_points.drivers.commands.get_driver_chart_info_command.starting.DriverStartPositionRecord;
import f1_Info.entry_points.drivers.commands.get_driver_chart_info_command.starting.GetDriverStartPositionsPerSeasonQueryData;
import f1_Info.entry_points.drivers.commands.get_driver_chart_info_command.starting.StartPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static common.utils.ListUtils.toSortedList;
import static java.util.stream.Collectors.*;

@Component(value = "GetDriverChartInfoCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Map<Integer, List<BigDecimal>> getPointsPerSeason(final String driverIdentifier) throws SQLException {
        return executeListQuery(new GetDriverSeasonPointsQueryData(driverIdentifier))
            .stream()
            .collect(groupingBy(DriverSeasonPointRecord::getYear, mapping(DriverSeasonPointRecord::getPoints, toSortedList(BigDecimal::compareTo))));
    }

    public Map<Integer, List<StartPosition>> getAllStartPositions(final String driverIdentifier) throws SQLException {
        return executeListQuery(new GetDriverStartPositionsPerSeasonQueryData(driverIdentifier))
            .stream()
            .collect(groupingBy(
                DriverStartPositionRecord::getYear,
                mapping(startRecord -> new StartPosition(startRecord.getPosition(), startRecord.getAmount()), toList())
            ));
    }
}