package common.constants.f1;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum PositionType {
    FINISHED("finished", null),
    RETIRED("retired", "R"),
    DISQUALIFIED("disqualified", "D"),
    EXCLUDED("excluded", "E"),
    WITHDRAWN("withdrawn", "W"),
    FAILED_TO_QUALIFY("failed to qualify", "F"),
    NOT_CLASSIFIED("not classified", "N");

    private final String mValue;
    private final String mErgastCode;

    public static PositionType fromString(final String string) {
        if (string == null) {
            throw new IllegalArgumentException("Unable to parse null to a position type");
        }

        final Optional<PositionType> positionType = Arrays.stream(values()).filter(positionType1 -> Objects.equals(positionType1.mErgastCode, string)).findFirst();
        if (positionType.isPresent()) {
            return positionType.get();
        }
        try {
            final int position = Integer.parseInt(string);
            if (position <= 0) {
                throw new NumberFormatException();
            }
            return PositionType.FINISHED;
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Unable to parse: %s to a valid position type", string));
        }
    }
}
