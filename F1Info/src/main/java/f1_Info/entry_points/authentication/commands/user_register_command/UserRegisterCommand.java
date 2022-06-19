package f1_Info.entry_points.authentication.commands.user_register_command;

import common.constants.email.Email;
import common.email.EmailSendOutParameters;
import common.email.EmailService;
import common.email.EmailType;
import f1_Info.communication.ClientUriFactory;
import f1_Info.configuration.web.users.F1UserDetails;
import f1_Info.configuration.web.users.UserManager;
import f1_Info.configuration.web.users.exceptions.UnableToRegisterUserException;
import f1_Info.entry_points.authentication.services.token_service.TokenService;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static f1_Info.configuration.web.ResponseUtil.conflict;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class UserRegisterCommand implements Command {
    private final Email mEmail;
    private final String mPassword;
    private final PasswordEncoder mPasswordEncoder;
    private final UserManager mUserManager;
    private final TokenService mTokenService;
    private final EmailService mEmailService;
    private final ClientUriFactory mClientUriFactory;

    @Override
    public String getAction() {
        return String.format("Register email: %s with password: *****", mEmail);
    }

    @Override
    public ResponseEntity<?> execute() {
        try {
            final long userId = mUserManager.registerUser(F1UserDetails.createNewUser(mEmail, mPasswordEncoder.encode(mPassword)));
            final UUID registerToken = UUID.randomUUID();

            mTokenService.insertTokenForUser(userId, registerToken);
            sendRegistrationEmail(registerToken);
            
            return ok();
        } catch (final UnableToRegisterUserException e) {
            return conflict("Unable to register this user, try again later!");
        }
    }

    private void sendRegistrationEmail(final UUID registerToken) {
        final boolean sentEmail = mEmailService.sendEmail(new EmailSendOutParameters(
            mEmail,
            "Verify Your Email Address for F1Info",
            String.format(UserRegistrationEmail.HTML_MESSAGE, mClientUriFactory.createSignUpUri(registerToken)),
            EmailType.REGISTRATION_VERIFICATION
        ));

        if (!sentEmail) {
            throw new UnableToRegisterUserException();
        }
    }
}
