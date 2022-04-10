package f1_Info.background.ergast_tasks.ergast.responses.standings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import f1_Info.background.ergast_tasks.ergast.responses.DriverData;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverStandingsData {
    int mPosition;
    BigDecimal mPoints;
    int mAmountOfWinsInSeasonSoFar;
    DriverData mDriverData;
    List<ConstructorData> mConstructorData;

    public DriverStandingsData(
        @JsonProperty("position") int position,
        @JsonProperty("points") BigDecimal points,
        @JsonProperty("wins") int amountOfWinsInSeasonSoFar,
        @JsonProperty("Driver") DriverData driverData,
        @JsonProperty("Constructors") List<ConstructorData> constructorData
    ) {
        mPosition = position;
        mPoints = points;
        mAmountOfWinsInSeasonSoFar = amountOfWinsInSeasonSoFar;
        mDriverData = driverData;
        mConstructorData = constructorData;
    }

    public String getConstructorIdentification() {
        return mConstructorData.get(mConstructorData.size() - 1).getConstructorIdentifier();
    }
}
