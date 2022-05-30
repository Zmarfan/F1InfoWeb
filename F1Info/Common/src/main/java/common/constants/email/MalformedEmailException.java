package common.constants.email;

import java.io.IOException;

public class MalformedEmailException extends IOException {
    public MalformedEmailException(final String message) {
        super(message);
    }
}
