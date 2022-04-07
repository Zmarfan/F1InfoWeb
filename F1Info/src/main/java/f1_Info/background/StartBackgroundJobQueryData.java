package f1_Info.background;

import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class StartBackgroundJobQueryData implements IQueryData<Integer> {
    int mTaskTypeId;

    @Override
    public String getStoredProcedureName() {
        return "background_start_background_job";
    }
}
