package f1_Info.entry_points.reports;

import common.helpers.DateFactory;
import f1_Info.background.ergast_tasks.ErgastFetchingInformation;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.reports.commands.get_driver_report_commands.all.GetAllDriverReportCommand;
import f1_Info.entry_points.reports.commands.get_driver_report_filter_values_command.Database;
import f1_Info.entry_points.reports.commands.get_driver_report_filter_values_command.GetDriverReportFilterValuesCommand;
import f1_Info.entry_points.reports.commands.get_driver_report_commands.individual.GetIndividualDriverReportCommand;
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
            if (!seasonIsValid(season) || sortColumn == null || sortColumn.isEmpty() && round > 0) {
                throw new BadRequestException("Invalid sort column");
            }

            return new GetAllDriverReportCommand(season, round, SortDirection.fromString(sortDirection), sortColumn, mDriverReportDatabase);
        });
    }

    @GetMapping("/get-individual-driver-report/{season}/{driverIdentifier}")
    public ResponseEntity<?> getIndividualDriverReport(
        @PathVariable("season") final int season,
        @PathVariable("driverIdentifier") final String driverIdentifier,
        @RequestParam("sortColumn") final String sortColumn,
        @RequestParam("sortDirection") final String sortDirection
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!seasonIsValid(season) || driverIdentifier == null || driverIdentifier.isEmpty() || sortColumn == null || sortColumn.isEmpty()) {
                throw new BadRequestException("Invalid sort column");
            }

            return new GetIndividualDriverReportCommand(season, driverIdentifier, SortDirection.fromString(sortDirection), sortColumn, mDriverReportDatabase);
        });
    }

    private boolean seasonIsValid(final int season) {
        return season >= ErgastFetchingInformation.FIRST_FORMULA_1_SEASON && season <= mDateFactory.now().getYear();
    }
}
