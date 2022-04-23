package f1_Info.background.ergast_tasks.ergast.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinishStatusData {
    String mStatus;

    public FinishStatusData(
        @JsonProperty("status") String status
    ) {
        mStatus = status;
    }
}
