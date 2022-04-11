package f1_Info.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum PositionType {
    FINISHED(1, null),
    RETIRED(2, "R"),
    DISQUALIFIED(3, "D"),
    EXCLUDED(4, "E"),
    WITHDRAWN(5, "W"),
    FAILED_TO_QUALIFY(6, "F"),
    NOT_CLASSIFIED(7, "N");

    final int mId;
    final String mValue;

    public static PositionType fromString(final String string) {
        final Optional<PositionType> positionType = Arrays.stream(values()).filter(positionType1 -> Objects.equals(positionType1.mValue, string)).findFirst();
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
