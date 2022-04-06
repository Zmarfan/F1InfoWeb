package f1_Info.background.test_query_datas;

import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class TestQueryData implements IQueryData<Integer> {
    @Override
    public String getStoredProcedureName() {
        return "test_procedure";
    }
}
