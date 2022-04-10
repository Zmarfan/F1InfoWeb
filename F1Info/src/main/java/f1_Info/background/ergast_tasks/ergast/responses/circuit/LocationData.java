package f1_Info.background.ergast_tasks.ergast.responses.circuit;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Country;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class LocationData {
    BigDecimal mLatitude;
    BigDecimal mLongitude;
    String mLocationName;
    Country mCountry;

    public LocationData(
        @JsonProperty("lat") BigDecimal latitude,
        @JsonProperty("long") BigDecimal longitude,
        @JsonProperty("locality") String locationName,
        @JsonProperty("country") String countryName
    ) {
        mLatitude = latitude;
        mLongitude = longitude;
        mLocationName = locationName;
        mCountry = Country.fromName(countryName);
    }
}