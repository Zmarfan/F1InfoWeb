package f1_Info.background.ergast_tasks.fetch_constructor_standings_task;

import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.background.ergast_tasks.ergast.responses.standings.ConstructorStandingsData;
import database.IQueryData;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class MergeIntoConstructorStandingsQueryData implements IQueryData<Void> {
    int m1RaceId;
    String m2ConstructorIdentifier;
    BigDecimal m3Points;
    int m4Position;
    String m5PositionType;
    int m6AmountOfWins;

    public MergeIntoConstructorStandingsQueryData(final ConstructorStandingsData constructorStandingsData, final RaceRecord raceRecord) {
        m1RaceId = raceRecord.getRaceId();
        m2ConstructorIdentifier = constructorStandingsData.getConstructorData().getConstructorIdentifier();
        m3Points = constructorStandingsData.getPoints();
        m4Position = constructorStandingsData.getPosition();
        m5PositionType = constructorStandingsData.getPositionType().getValue();
        m6AmountOfWins = constructorStandingsData.getAmountOfWinsInSeasonSoFar();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_constructor_standing_if_not_present";
    }
}
