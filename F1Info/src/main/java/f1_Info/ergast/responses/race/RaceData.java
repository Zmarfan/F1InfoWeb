package f1_Info.ergast.responses.race;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Url;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.utils.DateUtils;
import lombok.Value;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

@Value
public class RaceData {
    int year;
    int round;
    Url wikipediaUrl;
    String raceName;
    Instant raceTime;
    Date raceDate;
    Date qualifyingDate;
    Date sprintDate;
    Date firstPracticeDate;
    Date secondPracticeDate;
    Date thirdPracticeDate;
    CircuitData circuitData;

    public RaceData(
        @JsonProperty("season") int year,
        @JsonProperty("round") int round,
        @JsonProperty("url") String wikipediaUrl,
        @JsonProperty("raceName") String raceName,
        @JsonProperty("time") String raceTimeStart,
        @JsonProperty("date") String raceDate,
        @JsonProperty("Qualifying") ErgastDate qualifyingDate,
        @JsonProperty("Sprint") ErgastDate sprintDate,
        @JsonProperty("FirstPractice") ErgastDate firstPracticeDate,
        @JsonProperty("SecondPractice") ErgastDate secondPracticeDate,
        @JsonProperty("ThirdPractice") ErgastDate thirdPracticeDate,
        @JsonProperty("Circuit") CircuitData circuitData
    ) throws MalformedURLException, ParseException {
        this.year = year;
        this.round = round;
        this.wikipediaUrl = new Url(wikipediaUrl);
        this.raceName = raceName;
        this.raceTime = raceTimeStart != null ? Instant.parse(raceTimeStart) : null;
        this.raceDate = DateUtils.parse(raceDate);
        this.qualifyingDate = qualifyingDate != null ? qualifyingDate.getDate() : null;
        this.sprintDate = sprintDate != null ? sprintDate.getDate() : null;
        this.firstPracticeDate = firstPracticeDate != null ? firstPracticeDate.getDate() : null;
        this.secondPracticeDate = secondPracticeDate != null ? secondPracticeDate.getDate() : null;
        this.thirdPracticeDate = thirdPracticeDate != null ? thirdPracticeDate.getDate() : null;
        this.circuitData = circuitData;
    }
}
