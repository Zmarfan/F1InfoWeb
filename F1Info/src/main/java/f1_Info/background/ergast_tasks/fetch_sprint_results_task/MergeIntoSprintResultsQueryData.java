package f1_Info.background.ergast_tasks.fetch_sprint_results_task;

import f1_Info.background.ergast_tasks.ergast.responses.results.ResultDataHolder;
import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class MergeIntoSprintResultsQueryData implements IQueryData<Void> {

    public MergeIntoSprintResultsQueryData(final ResultDataHolder resultDataHolder) {

    }

    @Override
    public String getStoredProcedureName() {
        return null;
    }
}
