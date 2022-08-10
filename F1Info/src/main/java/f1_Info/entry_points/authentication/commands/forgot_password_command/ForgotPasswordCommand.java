package f1_Info.entry_points.authentication.commands.forgot_password_command;

import common.constants.email.Email;
import common.email.EmailSendOutParameters;
import common.email.EmailService;
import common.email.EmailType;
import f1_Info.communication.ClientUriFactory;
import f1_Info.configuration.web.users.F1UserDetails;
import f1_Info.configuration.web.users.UserManager;
import f1_Info.entry_points.authentication.services.token_service.TokenService;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static f1_Info.configuration.web.ResponseUtil.*;

@AllArgsConstructor
public class ForgotPasswordCommand implements Command {
    private final Email mEmail;
    private final TokenService mTokenService;
    private final UserManager mUserManager;
    private final EmailService mEmailService;
    private final ClientUriFactory mClientUriFactory;

    @Override
    public ResponseEntity<?> execute() {
        final Optional<F1UserDetails> userDetails = getUserDetails();
        if (userDetails.isEmpty()) {
            return forbidden();
        }

        final UUID token = UUID.randomUUID();
        mTokenService.insertTokenForUser(userDetails.get().getUserId().orElseThrow(), token);
        if (!sendPasswordResetEmail(token)) {
            return internalServerError();
        }

        return ok();
    }

    private boolean sendPasswordResetEmail(final UUID token) {
        return mEmailService.sendEmail(new EmailSendOutParameters(
            mEmail,
            "Reset your password for F1Info",
            String.format(ForgotPasswordEmail.HTML_MESSAGE, mClientUriFactory.createResetPasswordUri(token)),
            EmailType.RESET_PASSWORD
        ));
    }

    private Optional<F1UserDetails> getUserDetails() {
        try {
            return Optional.of(mUserManager.loadUserByUsername(mEmail.read())).filter(F1UserDetails::isEnabled);
        } catch (final UsernameNotFoundException e) {
            return Optional.empty();
        }
    }
}
