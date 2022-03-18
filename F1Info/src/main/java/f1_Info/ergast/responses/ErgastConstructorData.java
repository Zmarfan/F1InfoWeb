package f1_Info.ergast.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Country;
import lombok.Value;

@Value
public class ErgastConstructorData {
    String constructorIdentifier;
    String wikipediaUrl;
    String name;
    Country nationality;

    public ErgastConstructorData(
        @JsonProperty("constructorId") String constructorIdentifier,
        @JsonProperty("url") String wikipediaUrl,
        @JsonProperty("name") String name,
        @JsonProperty("nationality") String nationality
    ) {
        this.constructorIdentifier = constructorIdentifier;
        this.wikipediaUrl = wikipediaUrl;
        this.name = name;
        this.nationality = Country.fromNationality(nationality);
    }
}
