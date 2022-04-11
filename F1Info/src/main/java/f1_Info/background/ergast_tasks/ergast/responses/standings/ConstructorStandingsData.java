package f1_Info.background.ergast_tasks.ergast.responses.standings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import lombok.Value;

import java.math.BigDecimal;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConstructorStandingsData {
    int mPosition;
    BigDecimal mPoints;
    int mAmountOfWinsInSeasonSoFar;
    ConstructorData mConstructorData;

    public ConstructorStandingsData(
        @JsonProperty("position") int position,
        @JsonProperty("points") BigDecimal points,
        @JsonProperty("wins") int amountOfWinsInSeasonSoFar,
        @JsonProperty("Constructor") ConstructorData constructorData
    ) {
        mPosition = position;
        mPoints = points;
        mAmountOfWinsInSeasonSoFar = amountOfWinsInSeasonSoFar;
        mConstructorData = constructorData;
    }
}
