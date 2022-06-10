package f1_Info.entry_points.authentication.commands.enable_user_command;

import f1_Info.entry_points.authentication.services.token_service.TokenStatusType;
import lombok.Value;

@Value
public class EnableUserErrorResponse {
    TokenStatusType mErrorType;
}
