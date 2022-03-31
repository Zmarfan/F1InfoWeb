package f1_Info.ergast.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Country;
import f1_Info.constants.Url;
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
