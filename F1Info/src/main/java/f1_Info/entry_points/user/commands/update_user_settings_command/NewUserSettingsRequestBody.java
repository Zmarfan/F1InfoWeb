package f1_Info.entry_points.user.commands.update_user_settings_command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@Value
public class NewUserSettingsRequestBody {
    String mDisplayName;

    @JsonCreator
    public NewUserSettingsRequestBody(
        @JsonProperty("displayName") String displayName
    ) {
        mDisplayName = displayName != null ? Jsoup.clean(displayName, Safelist.none()) : null;
    }
}
