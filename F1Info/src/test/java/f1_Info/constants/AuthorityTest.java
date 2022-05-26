package f1_Info.constants;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThrows(IllegalArgumentException.class, () -> Authority.fromStringCode("ädmin"));
    }

    @Test
    void should_throw_illegal_argument_exception_if_trying_to_null_string_code() {
        assertThrows(IllegalArgumentException.class, () -> Authority.fromStringCode(null));
    }
}
