package f1_Info.background;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Tasks {
    FETCH_CIRCUITS_TASK(1, "Fetch Circuits Task"),
    FETCH_DRIVERS_TASK(2, "Fetch Drivers Task"),
    FETCH_RACES_TASK(3, "Fetch Races Task"),
    FETCH_SEASONS_TASK(4, "Fetch Seasons Task"),
    FETCH_CONSTRUCTORS_TASK(5, "Fetch Constructors Task"),
    FETCH_FINISH_STATUS_TASK(6, "Fetch Finish Status Task");

    private final int id;
    private final String name;
}
