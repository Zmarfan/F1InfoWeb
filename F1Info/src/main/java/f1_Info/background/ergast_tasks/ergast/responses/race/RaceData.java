package f1_Info.background.ergast_tasks.ergast.responses.race;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.constants.Url;
import common.utils.DateUtils;
import f1_Info.background.ergast_tasks.ergast.responses.circuit.CircuitData;
import lombok.Value;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalTime;

@Value
public class RaceData {
    int mYear;
    int mRound;
    Url mWikipediaUrl;
    String mRaceName;
    LocalTime mRaceTime;
    LocalDate mRaceDate;
    LocalTime mQualifyingTime;
    LocalDate mQualifyingDate;
    LocalTime mSprintTime;
    LocalDate mSprintDate;
    LocalTime mFirstPracticeTime;
    LocalDate mFirstPracticeDate;
    LocalTime mSecondPracticeTime;
    LocalDate mSecondPracticeDate;
    LocalTime mThirdPracticeTime;
    LocalDate mThirdPracticeDate;
    CircuitData mCircuitData;

    public RaceData(
        @JsonProperty("season") int year,
        @JsonProperty("round") int round,
        @JsonProperty("url") String wikipediaUrl,
        @JsonProperty("raceName") String raceName,
        @JsonProperty("time") String raceTimeStart,
        @JsonProperty("date") String raceDate,
        @JsonProperty("Qualifying") ErgastSessionTimes qualifyingDateAndTime,
        @JsonProperty("Sprint") ErgastSessionTimes sprintDateAndTime,
        @JsonProperty("FirstPractice") ErgastSessionTimes firstPracticeDateAndTime,
        @JsonProperty("SecondPractice") ErgastSessionTimes secondPracticeDateAndTime,
        @JsonProperty("ThirdPractice") ErgastSessionTimes thirdPracticeDateAndTime,
        @JsonProperty("Circuit") CircuitData circuitData
    ) throws MalformedURLException {
        mYear = year;
        mRound = round;
        mWikipediaUrl = new Url(wikipediaUrl);
        mRaceName = raceName;
        mRaceTime = raceTimeStart != null ? DateUtils.parseTime(raceTimeStart) : null;
        mRaceDate = DateUtils.parse(raceDate);
        mQualifyingTime = qualifyingDateAndTime != null ? qualifyingDateAndTime.getTime() : null;
        mQualifyingDate = qualifyingDateAndTime != null ? qualifyingDateAndTime.getDate() : null;
        mSprintTime = sprintDateAndTime != null ? sprintDateAndTime.getTime() : null;
        mSprintDate = sprintDateAndTime != null ? sprintDateAndTime.getDate() : null;
        mFirstPracticeTime = firstPracticeDateAndTime != null ? firstPracticeDateAndTime.getTime() : null;
        mFirstPracticeDate = firstPracticeDateAndTime != null ? firstPracticeDateAndTime.getDate() : null;
        mSecondPracticeTime = secondPracticeDateAndTime != null ? secondPracticeDateAndTime.getTime() : null;
        mSecondPracticeDate = secondPracticeDateAndTime != null ? secondPracticeDateAndTime.getDate() : null;
        mThirdPracticeTime = thirdPracticeDateAndTime != null ? thirdPracticeDateAndTime.getTime() : null;
        mThirdPracticeDate = thirdPracticeDateAndTime != null ? thirdPracticeDateAndTime.getDate() : null;
        mCircuitData = circuitData;
    }
}
