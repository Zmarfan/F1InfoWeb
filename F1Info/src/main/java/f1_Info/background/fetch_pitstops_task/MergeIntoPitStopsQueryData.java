package f1_Info.background.fetch_pitstops_task;

import f1_Info.database.IQueryData;
import f1_Info.background.ergast.responses.pit_stop.PitStopData;
import lombok.Value;

import java.math.BigDecimal;
import java.sql.Time;

@Value
public class MergeIntoPitStopsQueryData implements IQueryData<Void> {
    int m1RaceId;
    String m2DriverIdentification;
    int m3Lap;
    int m4Stop;
    Time m5Time;
    BigDecimal m6DurationInSeconds;

    public MergeIntoPitStopsQueryData(final PitStopData pitStopData, final PitStopFetchInformationRecord fetchInformationRecord) {
        m1RaceId = fetchInformationRecord.getRaceId();
        m2DriverIdentification = pitStopData.getDriverIdentification();
        m3Lap = pitStopData.getLap();
        m4Stop = pitStopData.getStop();
        m5Time = pitStopData.getTime();
        m6DurationInSeconds = pitStopData.getDurationInSeconds();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_pit_stop_if_not_present";
    }
}
