package f1_Info.entry_points.reports.commands.get_race_report_commands;

import common.configuration.Configuration;
import common.constants.f1.ResultType;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.reports.SortDirection;
import f1_Info.entry_points.reports.commands.get_race_report_commands.overview.GetOverviewReportRowsQueryData;
import f1_Info.entry_points.reports.commands.get_race_report_commands.overview.RaceOverviewRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "GetRaceReportCommandsDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public List<RaceOverviewRecord> getOverviewReportRows(
        final int season,
        final ResultType resultType,
        final SortDirection sortDirection,
        final String sortColumn
    ) throws SQLException {
        return executeListQuery(new GetOverviewReportRowsQueryData(season, resultType.getStringCode(), sortDirection.getDirection(), sortColumn));
    }
}