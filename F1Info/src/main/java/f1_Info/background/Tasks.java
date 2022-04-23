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
    FETCH_FINISH_STATUS_TASK(6, "Fetch Finish Status Task"),
    FETCH_PIT_STOPS_TASK(7, "Fetch Pit Stops Task"),
    FETCH_LAP_TIMES_TASK(8, "Fetch Lap Times Task"),
    FETCH_DRIVER_STANDINGS_TASK(9, "Fetch Driver Standings Task"),
    FETCH_CONSTRUCTOR_STANDINGS_TASK(10, "Fetch Constructor Standings Task"),
    FETCH_SPRINT_RESULTS_TASK(11, "Fetch Sprint Results Task");

    private final int mId;
    private final String mName;
}
