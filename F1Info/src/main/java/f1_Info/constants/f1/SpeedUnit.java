package f1_Info.constants.f1;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SpeedUnit {
    KPH("kph"),
    MPH("mph");

    private final String mStringCode;

    public static SpeedUnit fromStringCode(final String stringCode) {
        return Arrays.stream(values())
            .filter(speedUnit -> speedUnit.mStringCode.equals(stringCode))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the stringCode: %s to a valid speed unit", stringCode)));
    }
}
