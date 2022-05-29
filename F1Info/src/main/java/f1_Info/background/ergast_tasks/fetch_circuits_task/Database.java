package f1_Info.background.ergast_tasks.fetch_circuits_task;

import common.configuration.Configuration;
import common.logger.Logger;
import database.BulkOfWork;
import f1_Info.background.TaskDatabase;
import f1_Info.background.ergast_tasks.ergast.responses.circuit.CircuitData;
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
        executeBulkOfWork(new BulkOfWork(circuitDataList.stream().map(MergeIntoCircuitDataQueryData::new).toList()));
    }
}
