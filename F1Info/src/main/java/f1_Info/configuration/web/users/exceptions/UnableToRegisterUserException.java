package f1_Info.configuration.web.users.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnableToRegisterUserException extends RuntimeException {
    public UnableToRegisterUserException(final String message) {
        super(message);
    }
}
