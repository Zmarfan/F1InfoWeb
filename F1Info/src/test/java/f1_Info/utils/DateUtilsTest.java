package f1_Info.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateUtilsTest {
    @Test
    void should_extract_seconds_from_time_as_expected() throws ParseException {
        assertEquals(BigDecimal.valueOf(78.371), DateUtils.parseTimeToSeconds("1:18.371"));
    }

    @Test
    void should_throw_parse_exception_if_unable_to_parse_given_time_to_seconds() {
        assertThrows(ParseException.class, () -> DateUtils.parseTimeToSeconds("1f:18:371"));
    }
}
