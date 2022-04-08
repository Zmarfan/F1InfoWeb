package f1_Info.background.fetch_races_task;

import f1_Info.constants.Url;
import f1_Info.database.IQueryData;
import f1_Info.ergast.responses.race.RaceData;
import lombok.Value;

import java.sql.Time;
import java.util.Date;

@Value
public class MergeIntoRacesQueryData  implements IQueryData<Void> {
    int m01Year;
    int m02Round;
    Url m03Url;
    String m04RaceName;
    Time m05RaceTime;
    Date m06RaceDate;
    Time m07QualifyingTime;
    Date m08QualifyingDate;
    Time m09SprintTime;
    Date m10SprintDate;
    Time m11FirstPracticeTime;
    Date m12FirstPracticeDate;
    Time m13SecondPracticeTime;
    Date m14SecondPracticeDate;
    Time m15ThirdPracticeTime;
    Date m16ThirdPracticeDate;
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
