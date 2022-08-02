package f1_Info.entry_points.authentication.commands.get_user_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetUserCommand implements Command {
    private final Long mUserId;

    @Override
    public String getAction() {
        return "Get User";
    }

    @Override
    public ResponseEntity<?> execute() throws Exception {
        if (mUserId == null) {
            return ok();
        }
        return ok(new GetUserResponse("TEST NAME"));
    }
}
