package f1_Info.background.ergast_tasks.ergast.responses.pit_stop;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.utils.DateUtils;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalTime;

@Value
public class PitStopData {
    String mDriverIdentification;
    int mLap;
    int mStop;
    LocalTime mTime;
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
        mTime = DateUtils.parseTime(time);
        mDurationInSeconds = durationInSeconds;
    }
}
