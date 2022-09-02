package f1_Info.entry_points.development;

import f1_Info.entry_points.development.commands.get_change_log_items_command.Database;
import f1_Info.entry_points.development.commands.get_change_log_items_command.GetChangeLogItemsCommand;
import f1_Info.entry_points.helper.EndpointHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/OpenDevelopment")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class OpenDevelopmentEndpoint {
    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;
    private final Database mDatabase;

    @GetMapping("/change-log-items")
    public ResponseEntity<?> getChangeLogItems() {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new GetChangeLogItemsCommand(mDatabase));
    }
}