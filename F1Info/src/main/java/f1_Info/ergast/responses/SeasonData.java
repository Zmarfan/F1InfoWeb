package f1_Info.ergast.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Url;
import lombok.Value;

import java.net.MalformedURLException;

@Value
public class SeasonData {
    int year;
    Url wikipediaUrl;

    public SeasonData(
        @JsonProperty("season") int year,
        @JsonProperty("url") String wikipediaUrl
    ) throws MalformedURLException {
        this.year = year;
        this.wikipediaUrl = new Url(wikipediaUrl);
    }
}
