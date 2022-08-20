package common.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@UtilityClass
public class ListUtils {
    public static String stringListToString(final List<String> list) {
        return String.join(", ", list);
    }

    public static <T> String listToString(final List<T> list, final Function<T, String> stringMapper) {
        return stringListToString(list.stream().map(stringMapper).toList());
    }

    public static <T> Collector<T,?, List<T>> toSortedList(Comparator<? super T> c) {
        return Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), l -> {
            l.sort(c);
            return l;
        });
    }
}
