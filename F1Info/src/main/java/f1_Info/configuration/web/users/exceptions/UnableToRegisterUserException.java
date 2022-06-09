package f1_Info.configuration.web.users.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnableToRegisterUserException extends RuntimeException {
    public UnableToRegisterUserException(final Throwable cause) {
        super(cause);
    }
}
