package f1_Info.background.fetch_constructors_task;

import f1_Info.constants.Country;
import f1_Info.constants.Url;
import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class MergeIntoConstructorDataQueryData implements IQueryData<Void> {
    String m1ConstructorIdentifier;
    String m2Name;
    Country m3Country;
    Url m4Url;

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_constructor_if_not_present";
    }
}
