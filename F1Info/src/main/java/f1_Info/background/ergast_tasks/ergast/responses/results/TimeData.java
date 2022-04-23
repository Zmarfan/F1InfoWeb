package f1_Info.background.ergast_tasks.ergast.responses.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Value
public class TimeData {
    BigDecimal mTimeInSeconds;
    String mDisplayTime;

    public TimeData(
        @JsonProperty("millis") Long timeInMilliseconds,
        @JsonProperty("time") String displayTime
    ) {
        mTimeInSeconds = timeInMilliseconds != null ? BigDecimal.valueOf(timeInMilliseconds).divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP) : null;
        mDisplayTime = displayTime;
    }

    public Optional<BigDecimal> getTimeInSeconds() {
        return Optional.ofNullable(mTimeInSeconds);
    }
}
