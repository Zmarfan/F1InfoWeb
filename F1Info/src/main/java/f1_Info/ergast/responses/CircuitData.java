package f1_Info.ergast.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class CircuitData {
    String circuitIdentifier;
    String wikipediaUrl;
    String circuitName;
    LocationData locationData;

    public CircuitData(
        @JsonProperty("circuitId") String circuitIdentifier,
        @JsonProperty("url") String wikipediaUrl,
        @JsonProperty("circuitName") String circuitName,
        @JsonProperty("Location") LocationData locationData
    ) {
        this.circuitIdentifier = circuitIdentifier;
        this.wikipediaUrl = wikipediaUrl;
        this.circuitName = circuitName;
        this.locationData = locationData;
    }
}
