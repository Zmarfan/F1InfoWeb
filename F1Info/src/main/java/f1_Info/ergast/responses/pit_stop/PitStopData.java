package f1_Info.ergast.responses.pit_stop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class PitStopData {
    String driverIdentification;
    int lap;
    int stop;
    String time;
    BigDecimal durationInSeconds;

    public PitStopData(
        @JsonProperty("driverId") String driverIdentification,
        @JsonProperty("lap") int lap,
        @JsonProperty("stop") int stop,
        @JsonProperty("time") String time,
        @JsonProperty("duration") BigDecimal durationInSeconds
    ) {
        this.driverIdentification = driverIdentification;
        this.lap = lap;
        this.stop = stop;
        this.time = time;
        this.durationInSeconds = durationInSeconds;
    }
}
