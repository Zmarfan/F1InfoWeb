package mock_data_generator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ConsoleColor {
    RESET("\033[0m"),
    FILE("\033[0;33m"),
    INFO("\033[0;34m"),
    ERROR("\033[0;31m");

    @Getter
    private final String code;
}
