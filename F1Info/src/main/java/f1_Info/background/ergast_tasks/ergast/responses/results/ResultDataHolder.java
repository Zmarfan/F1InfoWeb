package f1_Info.background.ergast_tasks.ergast.responses.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultDataHolder {
    int mSeason;
    int mRound;
    List<ResultData> mSprintResultData;
    List<ResultData> mRaceResultData;

    public ResultDataHolder(
        @JsonProperty("season") int season,
        @JsonProperty("round") int round,
        @JsonProperty("SprintResults") List<ResultData> sprintResults,
        @JsonProperty("Results") List<ResultData> raceResults
    ) {
        mSeason = season;
        mRound = round;
        mSprintResultData = sprintResults;
        mRaceResultData = raceResults;
    }
}
