package f1_Info.entry_points.reports;

import common.helpers.DateFactory;
import f1_Info.background.ergast_tasks.ErgastFetchingInformation;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.reports.commands.get_drivers_from_season_command.Database;
import f1_Info.entry_points.reports.commands.get_drivers_from_season_command.GetDriversFromSeasonCommand;
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
    private final Database mDriversFromSeasonDatabase;
    private final DateFactory mDateFactory;

    @GetMapping("/drivers-from-season/{season}")
    public ResponseEntity<?> driversFromSeason(@PathVariable("season") final int season) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (season < ErgastFetchingInformation.FIRST_FORMULA_1_SEASON || season > mDateFactory.now().getYear()) {
                throw new BadRequestException();
            }

            return new GetDriversFromSeasonCommand(season, mDriversFromSeasonDatabase);
        });
    }
}
