package f1_Info.background.ergast_tasks.fetch_constructors_task;

import common.constants.Country;
import common.constants.Url;
import database.IQueryData;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import lombok.Value;

@Value
public class MergeIntoConstructorDataQueryData implements IQueryData<Void> {
    String m1ConstructorIdentifier;
    String m2Name;
    Country m3Country;
    Url m4Url;

    public MergeIntoConstructorDataQueryData(final ConstructorData constructorData) {
        m1ConstructorIdentifier = constructorData.getConstructorIdentifier();
        m2Name = constructorData.getName();
        m3Country = constructorData.getCountry();
        m4Url = constructorData.getWikipediaUrl();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_constructor_if_not_present";
    }
}
