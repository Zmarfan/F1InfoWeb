package common.constants;

import lombok.ToString;
import org.apache.commons.validator.routines.EmailValidator;

@ToString
public class Email {
    private final String mEmail;

    public Email(final String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException(String.format("Provided email is not valid: %s", email));
        }
        mEmail = email;
    }

    public String read() {
        return mEmail;
    }
}
