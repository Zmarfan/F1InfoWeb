package f1_Info.background.ergast_tasks.fetch_lap_times_task;

import f1_Info.background.ergast_tasks.RaceRecord;
import database.IQueryData;
import lombok.Value;

@Value
public class SetLastFetchedRaceLapTimesFetchingQueryData implements IQueryData<Void> {
    int m1LastFetchedSeason;
    int m2LastFetchedRound;

    public SetLastFetchedRaceLapTimesFetchingQueryData(final RaceRecord fetchInformation) {
        m1LastFetchedSeason = fetchInformation.getSeason();
        m2LastFetchedRound = fetchInformation.getRound();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_set_last_fetched_race_for_lap_times_fetching";
    }
}
