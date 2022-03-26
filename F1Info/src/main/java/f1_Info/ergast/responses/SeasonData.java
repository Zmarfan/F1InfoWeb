package f1_Info.ergast.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class SeasonData {
    int year;
    String wikipediaUrl;

    public SeasonData(
        @JsonProperty("season") int year,
        @JsonProperty("url") String wikipediaUrl
    ) {
        this.year = year;
        this.wikipediaUrl = wikipediaUrl;
    }
}
