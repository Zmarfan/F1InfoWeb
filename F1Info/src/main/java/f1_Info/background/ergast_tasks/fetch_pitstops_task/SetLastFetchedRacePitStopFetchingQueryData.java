package f1_Info.background.ergast_tasks.fetch_pitstops_task;

import f1_Info.background.ergast_tasks.RaceRecord;
import database.IQueryData;
import lombok.Value;

@Value
public class SetLastFetchedRacePitStopFetchingQueryData implements IQueryData<Void> {
    int m1LastFetchedSeason;
    int m2LastFetchedRound;

    public SetLastFetchedRacePitStopFetchingQueryData(final RaceRecord raceRecord) {
        m1LastFetchedSeason = raceRecord.getSeason();
        m2LastFetchedRound = raceRecord.getRound();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_set_last_fetched_race_for_pitstop_fetching";
    }
}
