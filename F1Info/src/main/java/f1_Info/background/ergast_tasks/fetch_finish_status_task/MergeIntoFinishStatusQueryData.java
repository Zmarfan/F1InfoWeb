package f1_Info.background.ergast_tasks.fetch_finish_status_task;

import f1_Info.database.IQueryData;
import f1_Info.background.ergast_tasks.ergast.responses.FinishStatusData;
import lombok.Value;

@Value
public class MergeIntoFinishStatusQueryData implements IQueryData<Void> {
    int m1Id;
    String m2Status;

    public MergeIntoFinishStatusQueryData(final FinishStatusData finishStatusData) {
        m1Id = finishStatusData.getId();
        m2Status = finishStatusData.getStatus();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_finish_status_if_not_present";
    }
}
