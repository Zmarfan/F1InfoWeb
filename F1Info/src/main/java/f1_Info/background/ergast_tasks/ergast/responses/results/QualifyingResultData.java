package f1_Info.background.ergast_tasks.ergast.responses.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import f1_Info.background.ergast_tasks.ergast.responses.DriverData;
import f1_Info.utils.DateUtils;
import lombok.Value;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Optional;

@Value
public class QualifyingResultData {
    int mNumber;
    int mPosition;
    DriverData mDriverData;
    ConstructorData mConstructorData;
    String mQ1DisplayTime;
    BigDecimal mQ1TimeInSeconds;
    String mQ2DisplayTime;
    BigDecimal mQ2TimeInSeconds;
    String mQ3DisplayTime;
    BigDecimal mQ3TimeInSeconds;

    public QualifyingResultData(
        @JsonProperty("number") int number,
        @JsonProperty("position") int position,
        @JsonProperty("Driver") DriverData driverData,
        @JsonProperty("Constructor") ConstructorData constructorData,
        @JsonProperty("Q1") String q1DisplayTime,
        @JsonProperty("Q2") String q2DisplayTime,
        @JsonProperty("Q3") String q3DisplayTime
    ) throws ParseException {
        mNumber = number;
        mPosition = position;
        mDriverData = driverData;
        mConstructorData = constructorData;
        mQ1DisplayTime = q1DisplayTime;
        mQ1TimeInSeconds = q1DisplayTime != null && !q1DisplayTime.isEmpty() ? DateUtils.parseTimeToSeconds(q1DisplayTime) : null;
        mQ2DisplayTime = q2DisplayTime;
        mQ2TimeInSeconds = q2DisplayTime != null && !q2DisplayTime.isEmpty() ? DateUtils.parseTimeToSeconds(q2DisplayTime) : null;
        mQ3DisplayTime = q3DisplayTime;
        mQ3TimeInSeconds = q3DisplayTime != null && !q3DisplayTime.isEmpty() ? DateUtils.parseTimeToSeconds(q3DisplayTime) : null;
    }

    public Optional<String> getQ1DisplayTime() {
        return Optional.ofNullable(mQ1DisplayTime);
    }

    public Optional<BigDecimal> getQ1TimeInSeconds() {
        return Optional.ofNullable(mQ1TimeInSeconds);
    }

    public Optional<String> getQ2DisplayTime() {
        return Optional.ofNullable(mQ2DisplayTime);
    }

    public Optional<BigDecimal> getQ2TimeInSeconds() {
        return Optional.ofNullable(mQ2TimeInSeconds);
    }

    public Optional<String> getQ3DisplayTime() {
        return Optional.ofNullable(mQ3DisplayTime);
    }

    public Optional<BigDecimal> getQ3TimeInSeconds() {
        return Optional.ofNullable(mQ3TimeInSeconds);
    }
}
