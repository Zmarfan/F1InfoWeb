package f1_Info.entry_points.reports;

import common.constants.f1.ResultType;
import common.helpers.DateFactory;
import f1_Info.background.ergast_tasks.ErgastFetchingInformation;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.reports.commands.get_constructor_report_commands.all.GetOverviewConstructorReportCommand;
import f1_Info.entry_points.reports.commands.get_constructor_report_commands.individual.GetIndividualConstructorReportCommand;
import f1_Info.entry_points.reports.commands.get_driver_report_commands.all.GetAllDriverReportCommand;
import f1_Info.entry_points.reports.commands.get_driver_report_commands.individual.GetIndividualDriverReportCommand;
import f1_Info.entry_points.reports.commands.get_race_report_commands.fastest_laps.GetFastestLapsReportCommand;
import f1_Info.entry_points.reports.commands.get_race_report_commands.overview.GetRaceOverviewReportCommand;
import f1_Info.entry_points.reports.commands.get_race_report_commands.pit_stops.GetPitStopsReportCommand;
import f1_Info.entry_points.reports.commands.get_race_report_commands.qualifying.GetQualifyingReportCommand;
import f1_Info.entry_points.reports.commands.get_race_report_commands.race_result.GetRaceResultReportCommand;
import f1_Info.entry_points.reports.commands.get_race_report_filter_values_command.GetRaceReportFilterValuesCommand;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.Database;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.constructor.GetConstructorReportFilterValuesCommand;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.driver.GetDriverReportFilterValuesCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Reports")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class ReportEndpoint {
    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;
    private final Database mDriverReportFilterDatabase;
    private final f1_Info.entry_points.reports.commands.get_driver_report_commands.Database mDriverReportDatabase;
    private final f1_Info.entry_points.reports.commands.get_constructor_report_commands.Database mConstructorReportDatabase;
    private final f1_Info.entry_points.reports.commands.get_race_report_filter_values_command.Database mRaceReportFilterDatabase;
    private final f1_Info.entry_points.reports.commands.get_race_report_commands.Database mRaceReportDatabase;
    private final DateFactory mDateFactory;

    @GetMapping("/driver-report-filter-values/{season}")
    public ResponseEntity<?> getDriverReportFilterValues(@PathVariable("season") final int season) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season)) {
                throw new BadRequestException();
            }

            return new GetDriverReportFilterValuesCommand(season, mDriverReportFilterDatabase);
        });
    }

    @GetMapping("/get-all-driver-report/{season}/{round}")
    public ResponseEntity<?> getAllDriverReport(
        @PathVariable("season") final int season,
        @PathVariable("round") final int round,
        @RequestParam("sortColumn") final String sortColumn,
        @RequestParam("sortDirection") final String sortDirection
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season) || sortColumn == null || sortColumn.isEmpty() || round <= 0) {
                throw new BadRequestException();
            }

            return new GetAllDriverReportCommand(season, round, SortDirection.fromString(sortDirection), sortColumn, mDriverReportDatabase);
        });
    }

    @GetMapping("/get-individual-driver-report/{season}/{driverIdentifier}/{raceType}")
    public ResponseEntity<?> getIndividualDriverReport(
        @PathVariable("season") final int season,
        @PathVariable("driverIdentifier") final String driverIdentifier,
        @PathVariable("raceType") final String raceType,
        @RequestParam("sortColumn") final String sortColumn,
        @RequestParam("sortDirection") final String sortDirection
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season) || driverIdentifier == null || driverIdentifier.isEmpty() || sortColumn == null || sortColumn.isEmpty()) {
                throw new BadRequestException();
            }

            return new GetIndividualDriverReportCommand(
                season,
                driverIdentifier,
                ResultType.fromStringCode(raceType),
                SortDirection.fromString(sortDirection),
                sortColumn,
                mDriverReportDatabase
            );
        });
    }

    @GetMapping("/constructor-report-filter-values/{season}")
    public ResponseEntity<?> getConstructorReportFilterValues(@PathVariable("season") final int season) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season)) {
                throw new BadRequestException();
            }

            return new GetConstructorReportFilterValuesCommand(season, mDriverReportFilterDatabase);
        });
    }

    @GetMapping("/get-overview-constructor-report/{season}/{round}")
    public ResponseEntity<?> getOverviewConstructorReport(
        @PathVariable("season") final int season,
        @PathVariable("round") final int round,
        @RequestParam("sortColumn") final String sortColumn,
        @RequestParam("sortDirection") final String sortDirection
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season) || sortColumn == null || sortColumn.isEmpty() || round <= 0) {
                throw new BadRequestException();
            }

            return new GetOverviewConstructorReportCommand(season, round, SortDirection.fromString(sortDirection), sortColumn, mConstructorReportDatabase);
        });
    }

    @GetMapping("/get-individual-constructor-report/{season}/{constructorIdentifier}/{raceType}")
    public ResponseEntity<?> getIndividualConstructorReport(
        @PathVariable("season") final int season,
        @PathVariable("constructorIdentifier") final String constructorIdentifier,
        @PathVariable("raceType") final String raceType,
        @RequestParam("sortColumn") final String sortColumn,
        @RequestParam("sortDirection") final String sortDirection
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season) || constructorIdentifier == null || constructorIdentifier.isEmpty() || sortColumn == null || sortColumn.isEmpty()) {
                throw new BadRequestException();
            }

            return new GetIndividualConstructorReportCommand(
                season,
                constructorIdentifier,
                ResultType.fromStringCode(raceType),
                SortDirection.fromString(sortDirection),
                sortColumn,
                mConstructorReportDatabase
            );
        });
    }

    @GetMapping("/race-report-filter-values/{season}")
    public ResponseEntity<?> getRaceReportFilterValues(@PathVariable("season") final int season) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season)) {
                throw new BadRequestException();
            }

            return new GetRaceReportFilterValuesCommand(season, mRaceReportFilterDatabase);
        });
    }

    @GetMapping("/get-overview-race-report/{season}/{type}")
    public ResponseEntity<?> getOverviewRaceResultReport(
        @PathVariable("season") final int season,
        @PathVariable("type") final String resultType,
        @RequestParam("sortColumn") final String sortColumn,
        @RequestParam("sortDirection") final String sortDirection
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season) || sortColumn == null || sortColumn.isEmpty()) {
                throw new BadRequestException("Invalid sort column");
            }

            return new GetRaceOverviewReportCommand(
                season,
                ResultType.fromStringCode(resultType),
                SortDirection.fromString(sortDirection),
                sortColumn,
                mRaceReportDatabase
            );
        });
    }

    @GetMapping("/get-race-result-report/{season}/{round}/{type}")
    public ResponseEntity<?> getRaceResultReport(
        @PathVariable("season") final int season,
        @PathVariable("round") final int round,
        @PathVariable("type") final String resultType,
        @RequestParam("sortColumn") final String sortColumn,
        @RequestParam("sortDirection") final String sortDirection
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season) || sortColumn == null || sortColumn.isEmpty() || round <= 0) {
                throw new BadRequestException();
            }

            return new GetRaceResultReportCommand(
                season,
                round,
                ResultType.fromStringCode(resultType),
                SortDirection.fromString(sortDirection),
                sortColumn,
                mRaceReportDatabase
            );
        });
    }

    @GetMapping("/get-fastest-laps-report/{season}/{round}")
    public ResponseEntity<?> getFastestLapsReport(
        @PathVariable("season") final int season,
        @PathVariable("round") final int round,
        @RequestParam("sortColumn") final String sortColumn,
        @RequestParam("sortDirection") final String sortDirection
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season) || sortColumn == null || sortColumn.isEmpty() || round <= 0) {
                throw new BadRequestException();
            }

            return new GetFastestLapsReportCommand(season, round, SortDirection.fromString(sortDirection), sortColumn, mRaceReportDatabase);
        });
    }

    @GetMapping("/get-pit-stops-report/{season}/{round}")
    public ResponseEntity<?> getPitStopsReport(
        @PathVariable("season") final int season,
        @PathVariable("round") final int round,
        @RequestParam("sortColumn") final String sortColumn,
        @RequestParam("sortDirection") final String sortDirection
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season) || sortColumn == null || sortColumn.isEmpty() || round <= 0) {
                throw new BadRequestException();
            }

            return new GetPitStopsReportCommand(season, round, SortDirection.fromString(sortDirection), sortColumn, mRaceReportDatabase);
        });
    }

    @GetMapping("/get-qualifying-report/{season}/{round}")
    public ResponseEntity<?> getQualifyingReport(
        @PathVariable("season") final int season,
        @PathVariable("round") final int round,
        @RequestParam("sortColumn") final String sortColumn,
        @RequestParam("sortDirection") final String sortDirection
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season) || sortColumn == null || sortColumn.isEmpty() || round <= 0) {
                throw new BadRequestException();
            }

            return new GetQualifyingReportCommand(season, round, SortDirection.fromString(sortDirection), sortColumn, mRaceReportDatabase);
        });
    }

    private boolean seasonIsValid(final int season) {
        return season >= ErgastFetchingInformation.FIRST_FORMULA_1_SEASON && season <= mDateFactory.now().getYear();
    }
}
