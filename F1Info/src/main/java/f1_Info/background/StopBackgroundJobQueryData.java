package f1_Info.background;

import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class StopBackgroundJobQueryData implements IQueryData<Void> {
    long m1TaskId;
    String m2ErrorMessage;

    @Override
    public String getStoredProcedureName() {
        return "background_stop_background_job";
    }
}
