package f1_Info.background;

import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RareDataFetchingTask {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;
    private final Logger mLogger;

    @Autowired
    public RareDataFetchingTask(
        ErgastProxy ergastProxy,
        Database database,
        Logger logger
    ) {
        mErgastProxy = ergastProxy;
        mDatabase = database;
        mLogger = logger;
    }

    public void run() {
        try {
            mLogger.logInfo("run", RareDataFetchingTask.class, "Started fetching constructor data");
            final List<ConstructorData> constructors = mErgastProxy.fetchAllConstructors();
            if (!constructors.isEmpty()) {
                mDatabase.mergeIntoConstructorsData(constructors);
            }

            mLogger.logInfo(
                "run",
                RareDataFetchingTask.class,
                String.format("Finished fetching constructor data, fetched and merged in a total of %d constructors", constructors.size())
            );
        } catch (final Exception e) {
            mLogger.logError("run", RareDataFetchingTask.class, "Failed to complete Data Fetching Task", e);
        }
    }
}
