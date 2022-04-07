package f1_Info.background.fetch_circuits_task;

import f1_Info.constants.Country;
import f1_Info.constants.Url;
import f1_Info.database.IQueryData;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class MergeIntoCircuitDataQueryData implements IQueryData<Void> {
    String m1CircuitIdentifier;
    String m2CircuitName;
    String m3LocationName;
    Country m4Country;
    BigDecimal m5Latitude;
    BigDecimal m6Longitude;
    Url m7Url;

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_circuit_if_not_present";
    }
}