package f1_Info.entry_points.homepage.commands.get_next_race_info_command;

import lombok.Value;

@Value
public class SessionInfo {
    SessionType mSessionType;
    String mSessionStartTimeMyTime;
    String mSessionApproxEndTime;
    String mSessionStartTimeTrack;
}
