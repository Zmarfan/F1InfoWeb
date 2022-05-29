package f1_Info.background.ergast_tasks.ergast.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.constants.Url;
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
