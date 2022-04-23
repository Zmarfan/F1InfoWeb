package f1_Info.background.ergast_tasks.fetch_finish_status_task;

import f1_Info.database.IQueryData;
import f1_Info.background.ergast_tasks.ergast.responses.FinishStatusData;
import lombok.Value;

@Value
public class MergeIntoFinishStatusQueryData implements IQueryData<Void> {
    String mStatus;

    public MergeIntoFinishStatusQueryData(final FinishStatusData finishStatusData) {
        mStatus = finishStatusData.getStatus();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_finish_status_if_not_present";
    }
}
