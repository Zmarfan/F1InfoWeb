package f1_Info.background;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Tasks {
    RARE_DATA_FETCHING_TASK(1, "RARE_DATA_FETCHING_TASK"),
    FETCH_DRIVERS_TASK(2, "Fetch Drivers Task"),
    FETCH_RACES_TASK(3, "Fetch Races Task");

    private final int id;
    private final String name;
}
