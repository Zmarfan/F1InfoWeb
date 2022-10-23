package f1_Info.entry_points.homepage;

import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.homepage.commands.get_next_race_info_command.GetNextRaceInfoCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Homepage")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class HomepageEndpoint {
    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;

    @GetMapping("/next-race")
    public ResponseEntity<?> getNextRaceInfo() {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new GetNextRaceInfoCommand());
    }
}
