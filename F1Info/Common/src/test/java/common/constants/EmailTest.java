package common.constants;

import common.constants.Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {
    @Test
    void should_return_same_email_as_provided_if_valid() {
        final String emailString = "coolboi@wow.com";
        final Email email = new Email(emailString);
        assertEquals(emailString, email.read());
    }

    @Test
    void should_throw_illegal_argument_exception_if_provided_email_is_invalid() {
        assertThrows(IllegalArgumentException.class, () -> new Email("coolboi.com"));
    }

    @Test
    void should_throw_illegal_argument_exception_if_provided_email_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }
}
