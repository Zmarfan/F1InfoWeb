package f1_Info.ergast.responses.circuit;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Url;
import lombok.Value;

import java.net.MalformedURLException;

@Value
public class CircuitData {
    String mCircuitIdentifier;
    Url mWikipediaUrl;
    String mCircuitName;
    LocationData mLocationData;

    public CircuitData(
        @JsonProperty("circuitId") String circuitIdentifier,
        @JsonProperty("url") String wikipediaUrl,
        @JsonProperty("circuitName") String circuitName,
        @JsonProperty("Location") LocationData locationData
    ) throws MalformedURLException {
        mCircuitIdentifier = circuitIdentifier;
        mWikipediaUrl = new Url(wikipediaUrl);
        mCircuitName = circuitName;
        mLocationData = locationData;
    }
}
