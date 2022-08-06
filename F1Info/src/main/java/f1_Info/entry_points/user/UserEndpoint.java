package f1_Info.entry_points.user;

import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.user.commands.NewUserSettingsRequestBody;
import f1_Info.entry_points.user.commands.UpdateUserSettingsCommand;
import f1_Info.entry_points.user.commands.UserSettingsValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/User")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class UserEndpoint {
    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;

    @PostMapping("/update-settings")
    public ResponseEntity<?> updateUserSettings(@RequestBody final NewUserSettingsRequestBody newUserSettings) {
        return mEndpointHelper.authorizeAndRun(mHttpServletRequest, userId -> {
            if (newUserSettings == null || !UserSettingsValidator.userSettingsAreValid(newUserSettings)) {
                throw new BadRequestException();
            }

            return new UpdateUserSettingsCommand(newUserSettings);
        });
    }
}
