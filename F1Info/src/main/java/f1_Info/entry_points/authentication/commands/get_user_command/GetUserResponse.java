package f1_Info.entry_points.authentication.commands.get_user_command;

import lombok.Value;

@Value
public class GetUserResponse {
    String mEmail;
    String mDisplayName;

    public GetUserResponse(final GetUserRecord userRecord) {
        mEmail = userRecord.getEmail();
        mDisplayName = userRecord.getDisplayName();
    }
}
