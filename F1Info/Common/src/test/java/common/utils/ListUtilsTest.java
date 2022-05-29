package common.utils;

import common.utils.ListUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListUtilsTest {

    @Test
    void should_return_string_list_formatted_to_string_in_correct_format() {
        final List<String> stringList = List.of("value1", "1", "1!½¤()!#(%(),.");
        final String formattedString = ListUtils.stringListToString(stringList);
        assertEquals("value1, 1, 1!½¤()!#(%(),.", formattedString);
    }

    @Test
    void should_return_list_formatted_to_string_formatted_through_mapper() {
        final List<Integer> list = List.of(1, 4, 12 ,2);
        final String formattedString = ListUtils.listToString(list, number -> String.format("#%d", number));
        assertEquals("#1, #4, #12, #2", formattedString);
    }
}
