package f1_Info.background;

import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataFetchingTask {
    private final ErgastProxy mErgastProxy;
    private final Logger mLogger;

    @Autowired
    public DataFetchingTask(ErgastProxy ergastProxy, Logger logger) {
        mErgastProxy = ergastProxy;
        mLogger = logger;
    }

    public void run() {
        try {
            final List<ConstructorData> constructors = mErgastProxy.fetchAllConstructors();
        } catch (final Exception e) {
            mLogger.logError("run", DataFetchingTask.class, "Failed to complete Data Fetching Task", e);
        }
    }
}
