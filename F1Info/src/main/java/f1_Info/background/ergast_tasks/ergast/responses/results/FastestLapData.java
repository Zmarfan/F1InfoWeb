package f1_Info.background.ergast_tasks.ergast.responses.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Optional;

@Value
public class FastestLapData {
    Integer mRank;
    int mLap;
    TimeData mTimeData;
    AverageSpeedData mAverageSpeedData;

    public FastestLapData(
        @JsonProperty("rank") Integer rank,
        @JsonProperty("lap") int lap,
        @JsonProperty("Time") TimeData timeData,
        @JsonProperty("AverageSpeed") AverageSpeedData averageSpeedData
    ) {
        mRank = rank;
        mLap = lap;
        mTimeData = timeData;
        mAverageSpeedData = averageSpeedData;
    }

    public Optional<Integer> getRank() {
        return Optional.ofNullable(mRank);
    }

    public Optional<AverageSpeedData> getAverageSpeedData() {
        return Optional.ofNullable(mAverageSpeedData);
    }
}
