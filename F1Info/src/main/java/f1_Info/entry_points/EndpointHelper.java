package f1_Info.entry_points;

import f1_Info.configuration.web.users.SessionAttributes;
import common.logger.Logger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.function.LongFunction;

import static f1_Info.configuration.web.ResponseUtil.badRequest;
import static f1_Info.configuration.web.ResponseUtil.internalServerError;

@AllArgsConstructor(onConstructor=@__({@Autowired}))
@Component
public class EndpointHelper {
    private final Logger mLogger;

    public ResponseEntity<?> runCommand(final HttpServletRequest request, final LongFunction<Command> createCommand) {
        final Command command;
        try {
            command = createCommand.apply(getUserIdFromSession(request));
        } catch (final IllegalArgumentException e) {
            return badRequest();
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

    private long getUserIdFromSession(final HttpServletRequest request) {
        return (long) request.getSession(false).getAttribute(SessionAttributes.USER_ID);
    }
}
