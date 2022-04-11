package f1_Info.background.ergast_tasks.ergast.responses.standings;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import f1_Info.background.ergast_tasks.ergast.responses.DriverData;
import f1_Info.constants.PositionType;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class DriverStandingsData {
    int mPosition;
    PositionType mPositionType;
    BigDecimal mPoints;
    int mAmountOfWinsInSeasonSoFar;
    DriverData mDriverData;
    List<ConstructorData> mConstructorData;

    public DriverStandingsData(
        @JsonProperty("position") int position,
        @JsonProperty("positionText") String positionText,
        @JsonProperty("points") BigDecimal points,
        @JsonProperty("wins") int amountOfWinsInSeasonSoFar,
        @JsonProperty("Driver") DriverData driverData,
        @JsonProperty("Constructors") List<ConstructorData> constructorData
    ) {
        mPosition = position;
        mPositionType = PositionType.fromString(positionText);
        mPoints = points;
        mAmountOfWinsInSeasonSoFar = amountOfWinsInSeasonSoFar;
        mDriverData = driverData;
        mConstructorData = constructorData;
    }

    public String getConstructorIdentification() {
        return mConstructorData.get(mConstructorData.size() - 1).getConstructorIdentifier();
    }
}
