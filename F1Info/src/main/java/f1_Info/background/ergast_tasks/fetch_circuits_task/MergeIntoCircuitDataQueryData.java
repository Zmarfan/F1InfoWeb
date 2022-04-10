package f1_Info.background.ergast_tasks.fetch_circuits_task;

import f1_Info.constants.Country;
import f1_Info.constants.Url;
import f1_Info.database.IQueryData;
import f1_Info.background.ergast_tasks.ergast.responses.circuit.CircuitData;
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

    public MergeIntoCircuitDataQueryData(final CircuitData circuitData) {
        m1CircuitIdentifier = circuitData.getCircuitIdentifier();
        m2CircuitName = circuitData.getCircuitName();
        m3LocationName = circuitData.getLocationData().getLocationName();
        m4Country = circuitData.getLocationData().getCountry();
        m5Latitude = circuitData.getLocationData().getLatitude();
        m6Longitude = circuitData.getLocationData().getLongitude();
        m7Url = circuitData.getWikipediaUrl();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_circuit_if_not_present";
    }
}
