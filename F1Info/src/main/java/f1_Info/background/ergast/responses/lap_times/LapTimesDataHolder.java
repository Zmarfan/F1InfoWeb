package f1_Info.background.ergast.responses.lap_times;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class LapTimesDataHolder {
    List<LapTimeData> mLapTimeData;

    public LapTimesDataHolder(
        @JsonProperty("Laps") List<LapTimeData> lapTimeData
    ) {
        mLapTimeData = lapTimeData;
    }
}
