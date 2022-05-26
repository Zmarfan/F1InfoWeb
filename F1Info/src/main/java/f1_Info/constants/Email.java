package f1_Info.constants;

import lombok.Getter;
import org.apache.commons.validator.routines.EmailValidator;

@Getter
public class Email {
    private final String mEmail;

    public Email(final String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException(String.format("Provided email is not valid: %s", email));
        }
        mEmail = email;
    }
}
