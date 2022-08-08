package f1_Info.entry_points.reports.commands.get_all_driver_report_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.reports.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "GetAllDriverReportCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public List<AllDriverReportRecord> getReportRows(final int season, final SortDirection sortDirection, final String sortColumn) throws SQLException {
        return executeListQuery(new GetAllDriverReportRowsQueryData(season, sortDirection.getDirection(), sortColumn));
    }
}
