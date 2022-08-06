package f1_Info.entry_points.authentication.commands.user_login_command.failed_login_handler;

import database.IQueryData;
import lombok.Value;

@Value
public class GetAmountOfFailedRequestsQueryData implements IQueryData<Integer> {
    String mIp;

    @Override
    public String getStoredProcedureName() {
        return "user_login_get_amount_of_failed_requests";
    }
}
