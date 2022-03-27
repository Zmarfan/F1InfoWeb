package f1_Info.background.rare_data_fetching_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class RareDataFetchingTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public RareDataFetchingTask(
        ErgastProxy ergastProxy,
        Database database,
        Logger logger
    ) {
        super(logger, database);
        mErgastProxy = ergastProxy;
        mDatabase = database;
    }

    @Override
    protected void runTask() throws SQLException {
        final List<CircuitData> circuits = mErgastProxy.fetchAllCircuits();
        if (!circuits.isEmpty()) {
            mDatabase.mergeIntoCircuitsData(circuits);
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.RARE_DATA_FETCHING_TASK;
    }
}
