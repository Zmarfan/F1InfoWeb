package common.constants.f1;

import common.constants.f1.FinishStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class FinishStatusTest {
    private static final String INVALID_CODE = "magsjuka";

    @Test
    void should_throw_illegal_argument_exception_if_parsing_invalid_finish_status_code() {
        assertThrows(IllegalArgumentException.class, () -> FinishStatus.fromStringCode(INVALID_CODE));
    }

    @Test
    void should_throw_illegal_argument_exception_if_parsing_null_finish_status_code() {
        assertThrows(IllegalArgumentException.class, () -> FinishStatus.fromStringCode(null));
    }

    @Test
    void should_return_false_when_checking_if_invalid_finish_status_code_exists() {
        assertFalse(FinishStatus.exists(INVALID_CODE));
    }

    @Test
    void should_return_false_when_checking_if_null_finish_status_code_exists() {
        assertFalse(FinishStatus.exists(null));
    }

    @ParameterizedTest
    @EnumSource(FinishStatus.class)
    void should_parse_finish_status_code_to_finish_status(final FinishStatus finishStatus) {
        assertEquals(finishStatus, FinishStatus.fromStringCode(finishStatus.getStringCode()));
    }

    @ParameterizedTest
    @EnumSource(FinishStatus.class)
    void should_return_true_when_checking_if_finish_status_string_code_exists(final FinishStatus finishStatus) {
        assertTrue(FinishStatus.exists(finishStatus.getStringCode()));
    }
}
