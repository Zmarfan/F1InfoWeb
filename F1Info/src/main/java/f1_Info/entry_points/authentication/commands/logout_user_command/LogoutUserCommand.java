package f1_Info.entry_points.authentication.commands.logout_user_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import static f1_Info.configuration.web.ResponseUtil.conflict;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class LogoutUserCommand implements Command {
    Long mUserId;
    HttpServletRequest mRequest;

    @Override
    public ResponseEntity<?> execute() {
        try {
            mRequest.logout();
        } catch (final ServletException e) {
            return conflict();
        }
        return ok();
    }
}
