package f1_Info.ergast.responses.race;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Url;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.utils.DateUtils;
import lombok.Value;

import java.net.MalformedURLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;

@Value
public class RaceData {
    int year;
    int round;
    Url wikipediaUrl;
    String raceName;
    Time raceTime;
    Date raceDate;
    Time qualifyingTime;
    Date qualifyingDate;
    Time sprintTime;
    Date sprintDate;
    Time firstPracticeTime;
    Date firstPracticeDate;
    Time secondPracticeTime;
    Date secondPracticeDate;
    Time thirdPracticeTime;
    Date thirdPracticeDate;
    CircuitData circuitData;

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
    ) throws MalformedURLException, ParseException {
        this.year = year;
        this.round = round;
        this.wikipediaUrl = new Url(wikipediaUrl);
        this.raceName = raceName;
        this.raceTime = raceTimeStart != null ? DateUtils.parseTime(raceTimeStart) : null;
        this.raceDate = DateUtils.parse(raceDate);
        this.qualifyingTime = qualifyingDateAndTime != null ? qualifyingDateAndTime.getTime() : null;
        this.qualifyingDate = qualifyingDateAndTime != null ? qualifyingDateAndTime.getDate() : null;
        this.sprintTime = sprintDateAndTime != null ? sprintDateAndTime.getTime() : null;
        this.sprintDate = sprintDateAndTime != null ? sprintDateAndTime.getDate() : null;
        this.firstPracticeTime = firstPracticeDateAndTime != null ? firstPracticeDateAndTime.getTime() : null;
        this.firstPracticeDate = firstPracticeDateAndTime != null ? firstPracticeDateAndTime.getDate() : null;
        this.secondPracticeTime = secondPracticeDateAndTime != null ? secondPracticeDateAndTime.getTime() : null;
        this.secondPracticeDate = secondPracticeDateAndTime != null ? secondPracticeDateAndTime.getDate() : null;
        this.thirdPracticeTime = thirdPracticeDateAndTime != null ? thirdPracticeDateAndTime.getTime() : null;
        this.thirdPracticeDate = thirdPracticeDateAndTime != null ? thirdPracticeDateAndTime.getDate() : null;
        this.circuitData = circuitData;
    }
}
