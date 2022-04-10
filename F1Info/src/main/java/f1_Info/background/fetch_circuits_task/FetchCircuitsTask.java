package f1_Info.background.fetch_circuits_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.background.ergast.ErgastProxy;
import f1_Info.background.ergast.responses.circuit.CircuitData;
import f1_Info.logger.Logger;
import f1_Info.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class FetchCircuitsTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchCircuitsTask(
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
            mergeIntoDatabase(circuits);
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_CIRCUITS_TASK;
    }

    private void mergeIntoDatabase(final List<CircuitData> circuits) throws SQLException {
        try {
            mDatabase.mergeIntoCircuitsData(circuits);
            mLogger.info(
                "mergeIntoDatabase",
                FetchCircuitsTask.class,
                String.format("Fetched a total of %d circuit entries from ergast and merged into database", circuits.size())
            );
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d entries for circuits into the database. Circuits: %s",
                circuits.size(),
                ListUtils.listToString(circuits, CircuitData::toString)
            ), e);
        }
    }
}
