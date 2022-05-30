package f1_Info.entry_points.helper;

import common.logger.Logger;
import f1_Info.configuration.web.users.SessionAttributes;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.function.Function;

import static f1_Info.configuration.web.ResponseUtil.badRequest;
import static f1_Info.configuration.web.ResponseUtil.internalServerError;

@AllArgsConstructor(onConstructor=@__({@Autowired}))
@Component
public class EndpointHelper {
    private final Logger mLogger;

    public ResponseEntity<?> runCommand(final HttpServletRequest request, final Function<Long, Command> createCommand) {
        final Command command;
        try {
            command = createCommand.apply(getUserIdFromSession(request));
        } catch (final BadRequestException e) {
            return badRequest(e.getMessage());
        } catch (final Exception e) {
            return internalServerError();
        }

        return executeCommand(command);
    }

    private ResponseEntity<?> executeCommand(Command command) {
        try {
            return command.execute();
        } catch (final Exception e) {
            mLogger.severe("execute", command.getClass(), String.format("Internal server error occurred in command while: %s", command.getAction()), e);
            return internalServerError();
        }
    }

    private Long getUserIdFromSession(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        return session != null ? (Long) session.getAttribute(SessionAttributes.USER_ID) : null;
    }
}
