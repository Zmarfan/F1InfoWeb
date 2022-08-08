package f1_Info.entry_points.reports;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SortDirection {
    ASCENDING("asc"),
    DESCENDING("desc");

    private final String mDirection;

    public static SortDirection fromString(final String direction) {
        return Arrays.stream(values())
            .filter(sortDirection -> sortDirection.mDirection.equals(direction))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the string code: %s to a valid sort direction", direction)));
    }
}
