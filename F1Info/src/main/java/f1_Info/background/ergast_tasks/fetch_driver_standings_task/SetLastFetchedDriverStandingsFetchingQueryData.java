package f1_Info.background.ergast_tasks.fetch_driver_standings_task;

import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class SetLastFetchedDriverStandingsFetchingQueryData implements IQueryData<Void> {
    int m1LastFetchedSeason;
    int m2LastFetchedRound;

    public SetLastFetchedDriverStandingsFetchingQueryData(final RaceRecord raceRecord) {
        m1LastFetchedSeason = raceRecord.getSeason();
        m2LastFetchedRound = raceRecord.getRound();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_set_last_fetched_race_for_driver_standings_fetching";
    }
}
