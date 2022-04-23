package f1_Info.background.ergast_tasks.fetch_results_tasks;

import f1_Info.background.ergast_tasks.ergast.responses.results.AverageSpeedData;
import f1_Info.background.ergast_tasks.ergast.responses.results.FastestLapData;
import f1_Info.background.ergast_tasks.ergast.responses.results.ResultData;
import f1_Info.background.ergast_tasks.ergast.responses.results.TimeData;
import f1_Info.constants.ResultType;
import f1_Info.database.IQueryData;
import f1_Info.utils.DateUtils;
import lombok.Value;

import java.math.BigDecimal;
import java.text.ParseException;

@Value
public class MergeIntoResultsQueryData implements IQueryData<Void> {
    String m01ResultType;
    int m02Season;
    int m03Round;
    String m04DriverIdentifier;
    String m05ConstructorIdentifier;
    int m06DriverNumber;
    int m07FinishPositionOrder;
    String m08PositionTypeCode;
    BigDecimal m09Points;
    int m10GridPosition;
    int m11CompletedLaps;
    String m12FinishStatusCode;
    String m13DisplayTime;
    BigDecimal m14TimeInSeconds;
    Integer m15FastestLapRank;
    Integer m16FastestLapLap;
    String m17FastestLapDisplayTime;
    BigDecimal m18FastestLapInSeconds;
    String m19FastestLapAverageSpeedUnit;
    BigDecimal m20FastestLapAverageSpeed;

    public MergeIntoResultsQueryData(final ResultType resultType, final ResultData resultData, final int season, final int round) throws ParseException {
        m01ResultType = resultType.getStringCode();
        m02Season = season;
        m03Round = round;
        m04DriverIdentifier = resultData.getDriverData().getDriverIdentifier();
        m05ConstructorIdentifier = resultData.getConstructorData().getConstructorIdentifier();
        m06DriverNumber = resultData.getNumber();
        m07FinishPositionOrder = resultData.getPosition();
        m08PositionTypeCode = resultData.getPositionType().getValue();
        m09Points = resultData.getPoints();
        m10GridPosition = resultData.getGridPosition();
        m11CompletedLaps = resultData.getCompletedLaps();
        m12FinishStatusCode = resultData.getFinishStatus().getStringCode();
        m13DisplayTime = resultData.getTimeData().map(TimeData::getDisplayTime).orElse(null);
        m14TimeInSeconds = resultData.getTimeData().flatMap(TimeData::getTimeInSeconds).orElse(null);
        m15FastestLapRank = resultData.getFastestLapData().flatMap(FastestLapData::getRank).orElse(null);
        m16FastestLapLap = resultData.getFastestLapData().map(FastestLapData::getLap).orElse(null);
        m17FastestLapDisplayTime = resultData.getFastestLapData().map(fastestTimeData -> fastestTimeData.getTimeData().getDisplayTime()).orElse(null);
        m18FastestLapInSeconds = DateUtils.parseTimeToSeconds(
            resultData.getFastestLapData().map(fastestLapData -> fastestLapData.getTimeData().getDisplayTime()).orElse(null)
        );
        m19FastestLapAverageSpeedUnit = resultData
            .getFastestLapData()
            .flatMap(FastestLapData::getAverageSpeedData)
            .map(speedData -> speedData.getSpeedUnit().getStringCode())
            .orElse(null);
        m20FastestLapAverageSpeed = resultData
            .getFastestLapData()
            .flatMap(FastestLapData::getAverageSpeedData)
            .map(AverageSpeedData::getSpeed)
            .orElse(null);
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_result_if_not_present";
    }
}
