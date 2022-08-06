package f1_Info.entry_points.user.commands;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class UpdateUserSettingsCommand implements Command {
    private NewUserSettingsRequestBody mNewUserSettings;

    @Override
    public String getAction() {
        return String.format("Update user settings with data: %s", mNewUserSettings);
    }

    @Override
    public ResponseEntity<?> execute() throws Exception {
        return ok();
    }
}
