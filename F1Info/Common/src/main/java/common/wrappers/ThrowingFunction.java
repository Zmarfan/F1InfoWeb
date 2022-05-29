package common.wrappers;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {
    R apply(final T argument) throws E;

    static <T, R> Function<T, R> wrapper(ThrowingFunction<T, R, Exception> function) {
        return argument -> {
            try {
                return function.apply(argument);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
