package f1_Info.ergast.responses.pit_stop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class PitStopDataHolder {
    List<PitStopData> pitStopData;

    public PitStopDataHolder(
        @JsonProperty("PitStops") List<PitStopData> pitStopData
    ) {
        this.pitStopData = pitStopData;
    }
}
