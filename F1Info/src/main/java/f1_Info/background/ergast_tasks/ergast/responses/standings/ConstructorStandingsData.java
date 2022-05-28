package f1_Info.background.ergast_tasks.ergast.responses.standings;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import f1_Info.constants.f1.PositionType;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class ConstructorStandingsData {
    int mPosition;
    PositionType mPositionType;
    BigDecimal mPoints;
    int mAmountOfWinsInSeasonSoFar;
    ConstructorData mConstructorData;

    public ConstructorStandingsData(
        @JsonProperty("position") int position,
        @JsonProperty("positionText") String positionText,
        @JsonProperty("points") BigDecimal points,
        @JsonProperty("wins") int amountOfWinsInSeasonSoFar,
        @JsonProperty("Constructor") ConstructorData constructorData
    ) {
        mPosition = position;
        mPositionType = PositionType.fromString(positionText);
        mPoints = points;
        mAmountOfWinsInSeasonSoFar = amountOfWinsInSeasonSoFar;
        mConstructorData = constructorData;
    }
}
