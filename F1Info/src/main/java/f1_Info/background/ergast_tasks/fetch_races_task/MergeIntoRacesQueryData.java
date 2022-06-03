package f1_Info.background.ergast_tasks.fetch_races_task;

import common.constants.Url;
import database.IQueryData;
import f1_Info.background.ergast_tasks.ergast.responses.race.RaceData;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Value
public class MergeIntoRacesQueryData  implements IQueryData<Void> {
    int m01Year;
    int m02Round;
    Url m03Url;
    String m04RaceName;
    LocalTime m05RaceTime;
    LocalDate m06RaceDate;
    LocalTime m07QualifyingTime;
    LocalDate m08QualifyingDate;
    LocalTime m09SprintTime;
    LocalDate m10SprintDate;
    LocalTime m11FirstPracticeTime;
    LocalDate m12FirstPracticeDate;
    LocalTime m13SecondPracticeTime;
    LocalDate m14SecondPracticeDate;
    LocalTime m15ThirdPracticeTime;
    LocalDate m16ThirdPracticeDate;
    String m17CircuitIdentifier;

    public MergeIntoRacesQueryData(final RaceData raceData) {
        m01Year = raceData.getYear();
        m02Round = raceData.getRound();
        m03Url = raceData.getWikipediaUrl();
        m04RaceName = raceData.getRaceName();
        m05RaceTime = raceData.getRaceTime();
        m06RaceDate = raceData.getRaceDate();
        m07QualifyingTime = raceData.getQualifyingTime();
        m08QualifyingDate = raceData.getQualifyingDate();
        m09SprintTime = raceData.getSprintTime();
        m10SprintDate = raceData.getSprintDate();
        m11FirstPracticeTime = raceData.getFirstPracticeTime();
        m12FirstPracticeDate = raceData.getFirstPracticeDate();
        m13SecondPracticeTime = raceData.getSecondPracticeTime();
        m14SecondPracticeDate = raceData.getSecondPracticeDate();
        m15ThirdPracticeTime = raceData.getThirdPracticeTime();
        m16ThirdPracticeDate = raceData.getThirdPracticeDate();
        m17CircuitIdentifier = raceData.getCircuitData().getCircuitIdentifier();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_race_if_not_present";
    }
}
