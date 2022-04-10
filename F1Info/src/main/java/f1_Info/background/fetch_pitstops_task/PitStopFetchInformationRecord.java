package f1_Info.background.fetch_pitstops_task;

import lombok.Value;

@Value
public class PitStopFetchInformationRecord {
    int mSeason;
    int mRound;
    int mRaceId;
}
