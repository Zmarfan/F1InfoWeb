package f1_Info.entry_points.reports.commands.get_driver_report_filter_values_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "GetDriverReportFilterValuesCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public List<DriverFromSeasonRecord> getDriversFromSeason(final int season) throws SQLException {
        return executeListQuery(new GetDriversFromSeasonQueryData(season));
    }

    public List<RaceFromSeasonRecord> getRacesFromSeason(final int season) throws SQLException {
        return executeListQuery(new GetRacesFromSeasonQueryData(season));
    }

    public boolean getSeasonHasSprint(final int season) throws SQLException {
        return executeBasicQuery(new GetSeasonHasSprintQueryData(season));
    }
}