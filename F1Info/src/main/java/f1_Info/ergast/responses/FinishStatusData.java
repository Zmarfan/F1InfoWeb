package f1_Info.ergast.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinishStatusData {
    int id;
    String status;

    public FinishStatusData(
        @JsonProperty("statusId") int id,
        @JsonProperty("status") String status
    ) {
        this.id = id;
        this.status = status;
    }
}
