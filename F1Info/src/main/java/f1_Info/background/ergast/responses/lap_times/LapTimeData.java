package f1_Info.background.ergast.responses.lap_times;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class LapTimeData {
    int mNumber;
    List<TimingData> mTimingData;

    public LapTimeData(
        @JsonProperty("number") int number,
        @JsonProperty("Timings") List<TimingData> timingData
    ) {
        mNumber = number;
        mTimingData = timingData;
    }
}
