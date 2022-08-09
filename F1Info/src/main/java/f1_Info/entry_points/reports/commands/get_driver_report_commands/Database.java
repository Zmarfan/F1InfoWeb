package f1_Info.entry_points.reports.commands.get_driver_report_commands;

import common.configuration.Configuration;
import common.constants.f1.ResultType;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.reports.SortDirection;
import f1_Info.entry_points.reports.commands.get_driver_report_commands.all.AllDriverReportRecord;
import f1_Info.entry_points.reports.commands.get_driver_report_commands.all.GetAllDriverReportRowsQueryData;
import f1_Info.entry_points.reports.commands.get_driver_report_commands.individual.GetIndividualDriverReportRowsQueryData;
import f1_Info.entry_points.reports.commands.get_driver_report_commands.individual.IndividualDriverReportRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "GetDriverReportCommandsDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public List<AllDriverReportRecord> getAllReportRows(final int season, final int round, final SortDirection sortDirection, final String sortColumn) throws SQLException {
        return executeListQuery(new GetAllDriverReportRowsQueryData(season, round, sortDirection.getDirection(), sortColumn));
    }

    public List<IndividualDriverReportRecord> getIndividualReportRows(
        final int season,
        final String driverIdentifier,
        final ResultType resultType, final SortDirection sortDirection,
        final String sortColumn
    ) throws SQLException {
        return executeListQuery(new GetIndividualDriverReportRowsQueryData(
            season,
            driverIdentifier,
            resultType.getStringCode(),
            sortDirection.getDirection(),
            sortColumn)
        );
    }
}
