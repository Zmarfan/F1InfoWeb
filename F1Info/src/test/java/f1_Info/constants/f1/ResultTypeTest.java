package f1_Info.constants.f1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResultTypeTest {

    @Test
    void should_throw_illegal_argument_exception_if_parsing_invalid_result_type_code() {
        assertThrows(IllegalArgumentException.class, () -> ResultType.fromStringCode("practice"));
    }

    @Test
    void should_throw_illegal_argument_exception_if_parsing_null_result_type_code() {
        assertThrows(IllegalArgumentException.class, () -> ResultType.fromStringCode(null));
    }

    @ParameterizedTest
    @EnumSource(ResultType.class)
    void should_parse_result_type_code_to_result_type(final ResultType resultType) {
        assertEquals(resultType, ResultType.fromStringCode(resultType.getStringCode()));
    }
}
