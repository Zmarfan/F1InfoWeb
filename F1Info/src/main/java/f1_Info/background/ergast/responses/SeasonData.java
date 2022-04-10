package f1_Info.background.ergast.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Url;
import lombok.Value;

import java.net.MalformedURLException;

@Value
public class SeasonData {
    int mYear;
    Url mWikipediaUrl;

    public SeasonData(
        @JsonProperty("season") int year,
        @JsonProperty("url") String wikipediaUrl
    ) throws MalformedURLException {
        mYear = year;
        mWikipediaUrl = new Url(wikipediaUrl);
    }
}
