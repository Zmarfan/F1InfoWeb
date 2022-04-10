package f1_Info.background.ergast_tasks.fetch_lap_times_task;

import f1_Info.database.IQueryData;
import f1_Info.background.ergast_tasks.ergast.responses.lap_times.TimingData;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class MergeIntoLapTimesQueryData implements IQueryData<Void> {
    int m1RaceId;
    String m2DriverIdentification;
    int m3Lap;
    int m4Position;
    String m5TimeString;
    BigDecimal m6LengthInSeconds;

    public MergeIntoLapTimesQueryData(final int lap, final TimingData timingData, final LapTimesFetchInformationRecord fetchInformation) {
        m1RaceId = fetchInformation.getRaceId();
        m2DriverIdentification = timingData.getDriverIdentification();
        m3Lap = lap;
        m4Position = timingData.getPosition();
        m5TimeString = timingData.getFormattedTime();
        m6LengthInSeconds = timingData.getTimeInSeconds();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_lap_time_if_not_present";
    }
}
