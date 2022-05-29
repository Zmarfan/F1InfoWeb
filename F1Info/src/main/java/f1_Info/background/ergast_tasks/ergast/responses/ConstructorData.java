package f1_Info.background.ergast_tasks.ergast.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.constants.Country;
import common.constants.Url;
import lombok.Value;

import java.net.MalformedURLException;

@Value
public class ConstructorData {
    String mConstructorIdentifier;
    Url mWikipediaUrl;
    String mName;
    Country mCountry;

    public ConstructorData(
        @JsonProperty("constructorId") String constructorIdentifier,
        @JsonProperty("url") String wikipediaUrl,
        @JsonProperty("name") String name,
        @JsonProperty("nationality") String nationality
    ) throws MalformedURLException {
        mConstructorIdentifier = constructorIdentifier;
        mWikipediaUrl = new Url(wikipediaUrl);
        mName = name;
        mCountry = Country.fromNationality(nationality);
    }
}
