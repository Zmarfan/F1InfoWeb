package f1_Info.constants.f1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class SpeedUnitTest {

    @Test
    void should_throw_illegal_argument_exception_if_parsing_invalid_speed_unit_code() {
        assertThrows(IllegalArgumentException.class, () -> SpeedUnit.fromStringCode("mps"));
    }

    @Test
    void should_throw_illegal_argument_exception_if_parsing_null_speed_unit_code() {
        assertThrows(IllegalArgumentException.class, () -> SpeedUnit.fromStringCode(null));
    }

    @ParameterizedTest
    @EnumSource(SpeedUnit.class)
    void should_parse_speed_unit_code_to_speed_unit(final SpeedUnit speedUnit) {
        assertEquals(speedUnit, SpeedUnit.fromStringCode(speedUnit.getStringCode()));
    }
}
