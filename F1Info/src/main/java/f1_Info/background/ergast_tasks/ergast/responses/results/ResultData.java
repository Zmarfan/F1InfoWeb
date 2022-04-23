package f1_Info.background.ergast_tasks.ergast.responses.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import f1_Info.background.ergast_tasks.ergast.responses.DriverData;
import f1_Info.constants.FinishStatus;
import f1_Info.constants.PositionType;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Optional;

@Value
public class ResultData {
    int mNumber;
    int mPosition;
    PositionType mPositionType;
    BigDecimal mPoints;
    int mGridPosition;
    int mCompletedLaps;
    FinishStatus mFinishStatus;
    TimeData mTimeData;
    FastestLapData mFastestLapData;
    DriverData mDriverData;
    ConstructorData mConstructorData;

    public ResultData(
        @JsonProperty("number") int number,
        @JsonProperty("position") int position,
        @JsonProperty("positionText") String positionTypeString,
        @JsonProperty("points") BigDecimal points,
        @JsonProperty("Driver") DriverData driverData,
        @JsonProperty("Constructor") ConstructorData constructorData,
        @JsonProperty("grid") int gridPosition,
        @JsonProperty("laps") int completedLaps,
        @JsonProperty("status") String finishStatusString,
        @JsonProperty("Time") TimeData timeData,
        @JsonProperty("FastestLap") FastestLapData fastestLapData
    ) {
        mNumber = number;
        mPosition = position;
        mPositionType = PositionType.fromString(positionTypeString);
        mPoints = points;
        mGridPosition = gridPosition;
        mCompletedLaps = completedLaps;
        mFinishStatus = FinishStatus.fromStringCode(finishStatusString);
        mTimeData = timeData;
        mFastestLapData = fastestLapData;
        mDriverData = driverData;
        mConstructorData = constructorData;
    }

    public Optional<TimeData> getTimeData() {
        return Optional.ofNullable(mTimeData);
    }

    public Optional<FastestLapData> getFastestLapData() {
        return Optional.ofNullable(mFastestLapData);
    }
}
