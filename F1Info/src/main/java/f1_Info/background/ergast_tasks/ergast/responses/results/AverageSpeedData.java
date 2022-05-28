package f1_Info.background.ergast_tasks.ergast.responses.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.f1.SpeedUnit;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class AverageSpeedData {
    SpeedUnit mSpeedUnit;
    BigDecimal mSpeed;

    public AverageSpeedData(
        @JsonProperty("units") String speedUnitString,
        @JsonProperty("speed") BigDecimal speed
    ) {
        mSpeedUnit = SpeedUnit.fromStringCode(speedUnitString);
        mSpeed = speed;
    }
}
