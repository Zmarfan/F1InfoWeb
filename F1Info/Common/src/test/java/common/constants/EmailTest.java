package common.constants;

import common.constants.email.Email;
import common.constants.email.MalformedEmailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {
    @Test
    void should_return_same_email_as_provided_if_valid() throws MalformedEmailException {
        final String emailString = "coolboi@wow.com";
        final Email email = new Email(emailString);
        assertEquals(emailString, email.read());
    }

    @Test
    void should_throw_malformed_email_exception_if_provided_email_is_invalid() {
        assertThrows(MalformedEmailException.class, () -> new Email("coolboi.com"));
    }

    @Test
    void should_throw_malformed_email_exception_if_provided_email_is_null() {
        assertThrows(MalformedEmailException.class, () -> new Email(null));
    }
}
