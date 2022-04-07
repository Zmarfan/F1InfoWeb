package f1_Info.background.fetch_finish_status_task;

import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class MergeIntoFinishStatusQueryData implements IQueryData<Void> {
    int m1Id;
    String m2Status;

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_finish_status_if_not_present";
    }
}
