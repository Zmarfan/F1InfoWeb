package f1_Info.background;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Tasks {
    RARE_DATA_FETCHING_TASK(1, "RARE_DATA_FETCHING_TASK");

    private final int id;
    private final String name;
}
