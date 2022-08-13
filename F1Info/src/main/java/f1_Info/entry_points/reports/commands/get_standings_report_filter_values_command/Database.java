package f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.constructor.ConstructorFromSeasonRecord;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.constructor.GetConstructorRacesFromSeasonQueryData;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.constructor.GetConstructorSeasonHasSprintQueryData;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.constructor.GetConstructorsFromSeasonQueryData;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.driver.DriverFromSeasonRecord;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.driver.GetDriverRacesFromSeasonQueryData;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.driver.GetDriverSeasonHasSprintQueryData;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.driver.GetDriversFromSeasonQueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "GetStandingsReportFilterValuesCommandDatabase")
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

    public List<RaceFromSeasonRecord> getDriverRacesFromSeason(final int season) throws SQLException {
        return executeListQuery(new GetDriverRacesFromSeasonQueryData(season));
    }

    public boolean getDriverSeasonHasSprint(final int season) throws SQLException {
        return executeBasicQuery(new GetDriverSeasonHasSprintQueryData(season));
    }

    public List<ConstructorFromSeasonRecord> getConstructorsFromSeason(final int season) throws SQLException {
        return executeListQuery(new GetConstructorsFromSeasonQueryData(season));
    }

    public List<RaceFromSeasonRecord> getConstructorRacesFromSeason(final int season) throws SQLException {
        return executeListQuery(new GetConstructorRacesFromSeasonQueryData(season));
    }

    public boolean getConstructorSeasonHasSprint(final int season) throws SQLException {
        return executeBasicQuery(new GetConstructorSeasonHasSprintQueryData(season));
    }
}