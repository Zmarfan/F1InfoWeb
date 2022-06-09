package f1_Info.entry_points.authentication.commands.enable_user_command;

import f1_Info.entry_points.authentication.services.register_token_service.RegisterTokenStatusType;
import lombok.Value;

@Value
public class EnableUserErrorResponse {
    RegisterTokenStatusType mErrorType;
}
