package f1_Info.entry_points.homepage.commands.get_next_race_info_command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SessionType {
    FIRST_PRACTICE("homepage.nextRace.firstPractice"),
    SECOND_PRACTICE("homepage.nextRace.secondPractice"),
    THIRD_PRACTICE("homepage.nextRace.thirdPractice"),
    RACE("homepage.nextRace.race"),
    QUALIFYING("homepage.nextRace.qualifying"),
    SPRINT("homepage.nextRace.sprint");

    private final String mKey;
}
