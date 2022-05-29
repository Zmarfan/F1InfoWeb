package f1_Info.configuration.web.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorityTest {
    @Test
    void should_return_user_authority_if_parsing_user_string_code() {
        assertEquals(Authority.USER, Authority.fromStringCode("user"));
    }

    @Test
    void should_return_admin_authority_if_parsing_admin_string_code() {
        assertEquals(Authority.ADMIN, Authority.fromStringCode("admin"));
    }

    @Test
    void should_throw_illegal_argument_exception_if_trying_to_parse_unknown_string_code() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Authority.fromStringCode("ädmin"));
    }

    @Test
    void should_throw_illegal_argument_exception_if_trying_to_null_string_code() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Authority.fromStringCode(null));
    }
}
