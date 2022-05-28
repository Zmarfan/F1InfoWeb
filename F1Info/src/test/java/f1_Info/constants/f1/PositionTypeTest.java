package f1_Info.constants.f1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PositionTypeTest {
    @Test
    void should_throw_illegal_argument_exception_if_parsing_null_ergast_code() {
        assertThrows(IllegalArgumentException.class, () -> PositionType.fromString(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3"})
    void should_parse_a_positive_number_in_string_to_finished_position_type(final String string) {
        assertEquals(PositionType.FINISHED, PositionType.fromString(string));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "-2"})
    void should_throw_illegal_argument_excpetion_if_parsing_invalid_numbers(final String string) {
        assertThrows(IllegalArgumentException.class, () -> PositionType.fromString(string));
    }

    @Test
    void should_parse_all_position_type_except_finished_based_on_ergast_value() {
        final long correctParses = Arrays.stream(PositionType.values())
            .filter(positionType -> !PositionType.FINISHED.equals(positionType))
            .filter(positionType -> positionType.equals(PositionType.fromString(positionType.getErgastCode())))
            .count();

        assertEquals(PositionType.values().length - 1, correctParses);
    }
}
