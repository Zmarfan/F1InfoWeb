package f1_Info.entry_points.authentication.commands.user_login_command.failed_login_handler;

import database.IQueryData;
import lombok.Value;

@Value
public class ResetFailedAttemptsQueryData implements IQueryData<Void> {
    String mIp;

    @Override
    public String getStoredProcedureName() {
        return "user_login_reset_failed_attempts";
    }
}
