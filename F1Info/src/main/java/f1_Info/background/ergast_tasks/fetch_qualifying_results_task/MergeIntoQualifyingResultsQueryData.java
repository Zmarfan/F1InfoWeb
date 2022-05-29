package f1_Info.background.ergast_tasks.fetch_qualifying_results_task;

import f1_Info.background.ergast_tasks.ergast.responses.results.QualifyingResultData;
import database.IQueryData;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class MergeIntoQualifyingResultsQueryData implements IQueryData<Void> {
    int m01Season;
    int m02Round;
    String m03DriverIdentifier;
    String m04ConstructorIdentifier;
    int m05DriverNumber;
    int m06Position;
    String m07Q1DisplayTime;
    BigDecimal m08Q1TimeInSeconds;
    String m09Q2DisplayTime;
    BigDecimal m10Q2TimeInSeconds;
    String m11Q3DisplayTime;
    BigDecimal m12Q3TimeInSeconds;

    public MergeIntoQualifyingResultsQueryData(final QualifyingResultData qualifyingResult, final int season, final int round) {
        m01Season = season;
        m02Round = round;
        m03DriverIdentifier = qualifyingResult.getDriverData().getDriverIdentifier();
        m04ConstructorIdentifier = qualifyingResult.getConstructorData().getConstructorIdentifier();
        m05DriverNumber = qualifyingResult.getNumber();
        m06Position = qualifyingResult.getPosition();
        m07Q1DisplayTime = qualifyingResult.getQ1DisplayTime().orElse(null);
        m08Q1TimeInSeconds = qualifyingResult.getQ1TimeInSeconds().orElse(null);
        m09Q2DisplayTime = qualifyingResult.getQ2DisplayTime().orElse(null);
        m10Q2TimeInSeconds = qualifyingResult.getQ2TimeInSeconds().orElse(null);
        m11Q3DisplayTime = qualifyingResult.getQ3DisplayTime().orElse(null);
        m12Q3TimeInSeconds = qualifyingResult.getQ3TimeInSeconds().orElse(null);
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_qualifying_result_if_not_present";
    }
}
