package f1_Info.background.ergast_tasks.fetch_pitstops_task;

import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class SetLastFetchedRacePitStopFetchingQueryData implements IQueryData<Void> {
    int m1LastFetchedSeason;
    int m2LastFetchedRound;

    public SetLastFetchedRacePitStopFetchingQueryData(final PitStopFetchInformationRecord pitStopFetchInformationRecord) {
        m1LastFetchedSeason = pitStopFetchInformationRecord.getSeason();
        m2LastFetchedRound = pitStopFetchInformationRecord.getRound();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_set_last_fetched_race_for_pitstop_fetching";
    }
}
