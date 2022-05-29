package f1_Info.background.ergast_tasks.fetch_drivers_task;

import common.constants.Country;
import common.constants.Url;
import database.IQueryData;
import f1_Info.background.ergast_tasks.ergast.responses.DriverData;
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

    public MergeIntoDriverDataQueryData(final DriverData driverData) {
        m1DriverIdentifier = driverData.getDriverIdentifier();
        m2PermanentNumber = driverData.getPermanentNumber().orElse(null);
        m3DriverCode = driverData.getCode().orElse(null);
        m4FirstName = driverData.getFirstName();
        m5LastName = driverData.getLastName();
        m6DateOfBirth = driverData.getDateOfBirth();
        m7Country = driverData.getCountry();
        m8Url = driverData.getWikipediaUrl();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_driver_if_not_present";
    }
}
