package f1_Info.entry_points.homepage.commands.get_next_race_info_command;

import lombok.Value;

import java.util.List;

@Value
public class NextRaceInfoResponse {
    int mRound;
    String mCountryCode;
    String mGrandPrixName;
    String mStartTime;
    String mEndTime;
    List<SessionInfo> mSessionInfo;
}
