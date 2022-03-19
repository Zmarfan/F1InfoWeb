package f1_Info.utils;

import java.util.List;
import java.util.function.Function;

public class ListUtils {
    public static String stringListToString(final List<String> list) {
        return String.join(", ", list);
    }

    public static <T> String listToString(final List<T> list, final Function<T, String> stringMapper) {
        return stringListToString(list.stream().map(stringMapper).toList());
    }
}
