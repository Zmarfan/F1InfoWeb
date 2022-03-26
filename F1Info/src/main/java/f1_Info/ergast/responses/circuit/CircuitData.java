package f1_Info.ergast.responses.circuit;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Url;
import lombok.Value;

import java.net.MalformedURLException;

@Value
public class CircuitData {
    String circuitIdentifier;
    Url wikipediaUrl;
    String circuitName;
    LocationData locationData;

    public CircuitData(
        @JsonProperty("circuitId") String circuitIdentifier,
        @JsonProperty("url") String wikipediaUrl,
        @JsonProperty("circuitName") String circuitName,
        @JsonProperty("Location") LocationData locationData
    ) throws MalformedURLException {
        this.circuitIdentifier = circuitIdentifier;
        this.wikipediaUrl = new Url(wikipediaUrl);
        this.circuitName = circuitName;
        this.locationData = locationData;
    }
}
