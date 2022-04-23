package f1_Info.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum SpeedUnit {
    KPH("kph"),
    MPH("mph");

    private final String mStringCode;

    public static SpeedUnit fromStringCode(final String stringCode) {
        return Arrays.stream(values())
            .filter(speedUnit -> Objects.equals(speedUnit.mStringCode, stringCode))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the stringCode: %s to a valid speed unit", stringCode)));
    }
}
