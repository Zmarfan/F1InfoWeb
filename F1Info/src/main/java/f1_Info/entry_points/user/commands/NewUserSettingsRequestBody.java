package f1_Info.entry_points.user.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class NewUserSettingsRequestBody {
    String mDisplayName;

    @JsonCreator
    public NewUserSettingsRequestBody(
        @JsonProperty("displayName") String displayName
    ) {
        mDisplayName = displayName;
    }
}
