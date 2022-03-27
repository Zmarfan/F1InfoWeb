package f1_Info.background.fetch_constructors_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class FetchConstructorsTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchConstructorsTask(
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
        final List<ConstructorData> constructors = mErgastProxy.fetchAllConstructors();
        if (!constructors.isEmpty()) {
            mDatabase.mergeIntoConstructorsData(constructors);
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_CONSTRUCTORS_TASK;
    }
}
