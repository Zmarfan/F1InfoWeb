package f1_Info.background.fetch_lap_times_task;

import lombok.Value;

@Value
public class LapTimesFetchInformationRecord {
    int mSeason;
    int mRound;
    int mRaceId;
}
