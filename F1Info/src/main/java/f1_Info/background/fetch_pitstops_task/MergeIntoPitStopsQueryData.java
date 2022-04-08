package f1_Info.background.fetch_pitstops_task;

import f1_Info.database.IQueryData;
import f1_Info.ergast.responses.pit_stop.PitStopData;
import lombok.Value;

import java.math.BigDecimal;
import java.sql.Time;

@Value
public class MergeIntoPitStopsQueryData implements IQueryData<Void> {
    int m1Season;
    int m2Round;
    String m3DriverIdentification;
    int m4Lap;
    int m5Stop;
    Time m6Time;
    BigDecimal m7DurationInSeconds;

    public MergeIntoPitStopsQueryData(final PitStopData pitStopData, final PitStopFetchInformationRecord fetchInformationRecord) {
        m1Season = fetchInformationRecord.getSeason();
        m2Round = fetchInformationRecord.getRound();
        m3DriverIdentification = pitStopData.getDriverIdentification();
        m4Lap = pitStopData.getLap();
        m5Stop = pitStopData.getStop();
        m6Time = pitStopData.getTime();
        m7DurationInSeconds = pitStopData.getDurationInSeconds();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_pit_stop_if_not_present";
    }
}
