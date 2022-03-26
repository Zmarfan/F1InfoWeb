package f1_Info.ergast.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Country;
import f1_Info.constants.Url;
import lombok.Value;

import java.net.MalformedURLException;

@Value
public class ConstructorData {
    String constructorIdentifier;
    Url wikipediaUrl;
    String name;
    Country country;

    public ConstructorData(
        @JsonProperty("constructorId") String constructorIdentifier,
        @JsonProperty("url") String wikipediaUrl,
        @JsonProperty("name") String name,
        @JsonProperty("nationality") String nationality
    ) throws MalformedURLException {
        this.constructorIdentifier = constructorIdentifier;
        this.wikipediaUrl = new Url(wikipediaUrl);
        this.name = name;
        this.country = Country.fromNationality(nationality);
    }
}
