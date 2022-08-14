package common.helpers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public class Pair<T, K> {
    private final T mFirst;
    private final K mSecond;

    public static <T, K>Pair<T, K> of(final T first, final K second) {
        return new Pair<>(first, second);
    }
}
