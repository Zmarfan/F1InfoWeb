package f1_Info.entry_points.authentication.commands.get_user_command;

import lombok.Value;

@Value
public class GetUserRecord {
    String mEmail;
    String mDisplayName;
}
