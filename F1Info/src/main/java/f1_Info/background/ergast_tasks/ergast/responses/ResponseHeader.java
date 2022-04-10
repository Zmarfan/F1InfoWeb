package f1_Info.background.ergast_tasks.ergast.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseHeader {
    long mLimit;
    long mOffset;
    long mTotal;

    public ResponseHeader(
        @JsonProperty("limit") long limit,
        @JsonProperty("offset") long offset,
        @JsonProperty("total") long total
    ) {
        mLimit = limit;
        mOffset = offset;
        mTotal = total;
    }
}
