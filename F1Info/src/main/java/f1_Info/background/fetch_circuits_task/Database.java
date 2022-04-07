package f1_Info.background.fetch_circuits_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.database.DatabaseBulkOfWork;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "FetchCircuitsTaskDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void mergeIntoCircuitsData(final List<CircuitData> circuitDataList) throws SQLException {
        final DatabaseBulkOfWork bulkOfWork = new DatabaseBulkOfWork();
        bulkOfWork.add(circuitDataList.stream().map(circuitData -> new MergeIntoCircuitDataQueryData(
            circuitData.getCircuitIdentifier(),
            circuitData.getCircuitName(),
            circuitData.getLocationData().getLocationName(),
            circuitData.getLocationData().getCountry(),
            circuitData.getLocationData().getLatitude(),
            circuitData.getLocationData().getLongitude(),
            circuitData.getWikipediaUrl()
        )).toList());
        executeBulkOfWork(bulkOfWork);
    }
}
