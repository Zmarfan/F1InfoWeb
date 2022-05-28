package f1_Info.constants;

import f1_Info.constants.f1.PositionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTypeTest {
    @Test
    void should_parse_a_positive_number_in_string_to_finished_position_type() {
        assertEquals(PositionType.FINISHED, PositionType.fromString("23"));
    }
}
