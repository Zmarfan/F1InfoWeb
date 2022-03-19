package f1_Info.ergast.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Country;
import lombok.Value;

@Value
public class ConstructorData {
    String constructorIdentifier;
    String wikipediaUrl;
    String name;
    Country country;

    public ConstructorData(
        @JsonProperty("constructorId") String constructorIdentifier,
        @JsonProperty("url") String wikipediaUrl,
        @JsonProperty("name") String name,
        @JsonProperty("nationality") String nationality
    ) {
        this.constructorIdentifier = constructorIdentifier;
        this.wikipediaUrl = wikipediaUrl;
        this.name = name;
        this.country = Country.fromNationality(nationality);
    }
}
