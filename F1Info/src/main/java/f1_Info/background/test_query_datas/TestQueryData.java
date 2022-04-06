package f1_Info.background.test_query_datas;

import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class TestQueryData implements IQueryData<Integer> {
    int m3TestParameter = 16;
    int m5TestParameter = 5;
    int m1TestParameter = 15;
    int m4TestParameter = 11;
    int m2TestParameter = 12;

    @Override
    public String getStoredProcedureName() {
        return "test_procedure";
    }
}
