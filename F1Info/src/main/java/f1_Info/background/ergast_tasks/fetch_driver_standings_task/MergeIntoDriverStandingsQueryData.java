package f1_Info.background.ergast_tasks.fetch_driver_standings_task;

import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.background.ergast_tasks.ergast.responses.standings.DriverStandingsData;
import f1_Info.database.IQueryData;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class MergeIntoDriverStandingsQueryData implements IQueryData<Void> {
    int m1RaceId;
    String m2DriverIdentification;
    String m3ConstructorIdentification;
    BigDecimal m4Points;
    int m5Position;
    int m6AmountOfWins;

    public MergeIntoDriverStandingsQueryData(final DriverStandingsData driverStandingsData, final RaceRecord raceRecord) {
        m1RaceId = raceRecord.getRaceId();
        m2DriverIdentification = driverStandingsData.getDriverData().getDriverIdentifier();
        m3ConstructorIdentification = driverStandingsData.getConstructorIdentification();
        m4Points = driverStandingsData.getPoints();
        m5Position = driverStandingsData.getPosition();
        m6AmountOfWins = driverStandingsData.getAmountOfWinsInSeasonSoFar();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_driver_standing_if_not_present";
    }
}