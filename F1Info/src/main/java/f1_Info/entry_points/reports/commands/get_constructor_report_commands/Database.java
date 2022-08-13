package f1_Info.entry_points.reports.commands.get_constructor_report_commands;

import common.configuration.Configuration;
import common.constants.f1.ResultType;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.reports.SortDirection;
import f1_Info.entry_points.reports.commands.get_constructor_report_commands.all.OverviewConstructorReportRecord;
import f1_Info.entry_points.reports.commands.get_constructor_report_commands.all.GetOverviewConstructorReportRowsQueryData;
import f1_Info.entry_points.reports.commands.get_constructor_report_commands.individual.GetIndividualConstructorReportRowsQueryData;
import f1_Info.entry_points.reports.commands.get_constructor_report_commands.individual.IndividualConstructorReportRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "GetConstructorReportCommandsDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public List<OverviewConstructorReportRecord> getOverviewReportRows(final int season, final int round, final SortDirection sortDirection, final String sortColumn) throws SQLException {
        return executeListQuery(new GetOverviewConstructorReportRowsQueryData(season, round, sortDirection.getDirection(), sortColumn));
    }

    public List<IndividualConstructorReportRecord> getIndividualReportRows(
        final int season,
        final String constructorIdentifier,
        final ResultType resultType,
        final SortDirection sortDirection,
        final String sortColumn
    ) throws SQLException {
        return executeListQuery(new GetIndividualConstructorReportRowsQueryData(
            season,
            constructorIdentifier,
            resultType.getStringCode(),
            sortDirection.getDirection(),
            sortColumn
        ));
    }
}
