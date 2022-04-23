package f1_Info.background.ergast_tasks.ergast.responses.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Optional;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeData {
    Long mTimeInMilliseconds;
    String mDisplayTime;

    public TimeData(
        @JsonProperty("millis") Long timeInMilliseconds,
        @JsonProperty("time") String displayTime
    ) {
        mTimeInMilliseconds = timeInMilliseconds;
        mDisplayTime = displayTime;
    }

    public Optional<Long> getTimeInMilliseconds() {
        return Optional.ofNullable(mTimeInMilliseconds);
    }
}
