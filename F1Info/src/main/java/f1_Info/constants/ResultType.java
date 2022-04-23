package f1_Info.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ResultType {
    RACE("race"),
    SPRINT("sprint");

    private final String mStringCode;

    public static ResultType fromStringCode(final String stringCode) {
        return Arrays.stream(values())
            .filter(resultType -> Objects.equals(resultType.mStringCode, stringCode))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the string code: %s to a valid result type", stringCode)));
    }
}
