package f1_Info.ergast.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErgastResponseHeader {
    long limit;
    long offset;
    long total;

    public ErgastResponseHeader(
        @JsonProperty("limit") long limit,
        @JsonProperty("offset") long offset,
        @JsonProperty("total") long total
    ) {
        this.limit = limit;
        this.offset = offset;
        this.total = total;
    }
}
