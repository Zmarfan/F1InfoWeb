package f1_Info.ergast.responses.pit_stop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class PitStopData {
    String mDriverIdentification;
    int mLap;
    int mStop;
    String mTime;
    BigDecimal mDurationInSeconds;

    public PitStopData(
        @JsonProperty("driverId") String driverIdentification,
        @JsonProperty("lap") int lap,
        @JsonProperty("stop") int stop,
        @JsonProperty("time") String time,
        @JsonProperty("duration") BigDecimal durationInSeconds
    ) {
        mDriverIdentification = driverIdentification;
        mLap = lap;
        mStop = stop;
        mTime = time;
        mDurationInSeconds = durationInSeconds;
    }
}
