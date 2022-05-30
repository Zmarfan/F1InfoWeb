package common.constants.email;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.validator.routines.EmailValidator;

@ToString
@EqualsAndHashCode
public class Email {
    private final String mEmail;

    public Email(final String email) throws MalformedEmailException {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new MalformedEmailException(String.format("Provided email is not valid: %s", email));
        }
        mEmail = email;
    }

    public String read() {
        return mEmail;
    }
}
