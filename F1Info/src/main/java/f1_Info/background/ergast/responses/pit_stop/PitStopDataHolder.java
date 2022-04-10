package f1_Info.background.ergast.responses.pit_stop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class PitStopDataHolder {
    List<PitStopData> mPitStopData;

    public PitStopDataHolder(
        @JsonProperty("PitStops") List<PitStopData> pitStopData
    ) {
        mPitStopData = pitStopData;
    }
}
