package f1_Info.entry_points.reports.commands.get_driver_report_commands.all;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum PositionMove {
    UP(1),
    DOWN(2),
    STAY(3);

    private final int mId;

    public static PositionMove fromId(final int id) {
        return Arrays.stream(values())
            .filter(positionMove -> id == positionMove.mId)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the id: %d to a valid Position Move", id)));
    }
}
