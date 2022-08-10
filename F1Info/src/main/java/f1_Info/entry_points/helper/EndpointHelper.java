package f1_Info.entry_points.helper;

import common.constants.email.Email;
import common.constants.email.MalformedEmailException;
import common.logger.Logger;
import f1_Info.configuration.web.users.Authority;
import f1_Info.configuration.web.users.SecurityContextWrapper;
import f1_Info.configuration.web.users.SessionAttributes;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.function.Function;

import static f1_Info.configuration.web.ResponseUtil.*;

@AllArgsConstructor(onConstructor=@__({@Autowired}))
@Component
public class EndpointHelper {
    private final SecurityContextWrapper mSecurityContextWrapper;
    private final Logger mLogger;

    public ResponseEntity<?> authorizeAndRun(final HttpServletRequest request, final Function<Long, Command> createCommand) {
        if (!isLoggedIn()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return runCommand(request, createCommand);
    }

    public ResponseEntity<?> runCommand(final HttpServletRequest request, final Function<Long, Command> createCommand) {
        final Long userId = getUserIdFromSession(request);
        final Command command;
        try {
            command = createCommand.apply(userId);
        } catch (final ForbiddenException e) {
            return forbidden(e.getMessage());
        } catch (final IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (final Exception e) {
            return internalServerError();
        }

        return executeCommand(userId, command);
    }

    public boolean isLoggedIn() {
        final Authentication authentication = mSecurityContextWrapper.getAuthentication();
        return !authentication.getAuthorities().contains(Authority.ROLE_ANONYMOUS) && authentication.isAuthenticated();
    }

    public Email convertEmail(final String email) {
        try {
            return new Email(email);
        } catch (final MalformedEmailException e) {
            throw new BadRequestException("The provided email is not a valid email address");
        }
    }

    private ResponseEntity<?> executeCommand(final Long userId, Command command) {
        try {
            return command.execute();
        } catch (final ForbiddenException e) {
            return forbidden();
        } catch (final BadRequestException e) {
            return badRequest();
        } catch (final Exception e) {
            mLogger.severe(
                "execute",
                command.getClass(),
                String.format("Internal server error occurred in command %s for user: %d", command.getClass().getName(), userId),
                e
            );
            return internalServerError();
        }
    }

    private Long getUserIdFromSession(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        return session != null ? (Long) session.getAttribute(SessionAttributes.USER_ID) : null;
    }
}
