package f1_Info.background.fetch_drivers_task;

import f1_Info.constants.Country;
import f1_Info.constants.Url;
import f1_Info.database.IQueryData;
import lombok.Value;

import java.util.Date;

@Value
public class MergeIntoDriverDataQueryData implements IQueryData<Void> {
    String m1DriverIdentifier;
    Integer m2PermanentNumber;
    String m3DriverCode;
    String m4FirstName;
    String m5LastName;
    Date m6DateOfBirth;
    Country m7Country;
    Url m8Url;

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_driver_if_not_present";
    }
}