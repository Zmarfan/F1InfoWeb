package f1_Info.background.ergast_tasks.fetch_pitstops_task;

import lombok.Value;

@Value
public class PitStopFetchInformationRecord {
    int mSeason;
    int mRound;
    int mRaceId;
}
