package f1_Info.entry_points.authentication.commands.get_user_command;

import lombok.Value;

@Value
public class GetUserResponse {
    String mDisplayName;
    String mEmail;

    public GetUserResponse(final GetUserRecord userRecord) {
        mDisplayName = userRecord.getEmail();
        mEmail = userRecord.getEmail();
    }
}
