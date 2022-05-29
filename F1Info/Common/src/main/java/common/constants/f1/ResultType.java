package common.constants.f1;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ResultType {
    RACE("race"),
    SPRINT("sprint");

    private final String mStringCode;

    public static ResultType fromStringCode(final String stringCode) {
        return Arrays.stream(values())
            .filter(resultType -> resultType.mStringCode.equals(stringCode))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the string code: %s to a valid result type", stringCode)));
    }
}
