package f1_Info.ergast.responses.circuit;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Country;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class LocationData {
    BigDecimal latitude;
    BigDecimal longitude;
    String locationName;
    Country country;

    public LocationData(
        @JsonProperty("lat") BigDecimal latitude,
        @JsonProperty("long") BigDecimal longitude,
        @JsonProperty("locality") String locationName,
        @JsonProperty("country") String countryName
    ) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
        this.country = Country.fromName(countryName);
    }
}