package f1_Info.background;

import f1_Info.background.rare_data_fetching_task.Database;
import f1_Info.background.rare_data_fetching_task.RareDataFetchingTask;
import f1_Info.logger.Logger;

import java.util.Optional;

public abstract class TaskWrapper {
    private final String mTaskName;

    protected final Logger mLogger;
    private final Database mDatabase;

    public TaskWrapper(
        final Database database,
        final Logger logger,
        final String taskName
    ) {
        mDatabase = database;
        mLogger = logger;
        mTaskName = taskName;
    }

    public void run() {
        final Optional<Long> id = startBackgroundJob();
        if (id.isEmpty()) {
            return;
        }

        Exception exception = null;
        try {
            runTask();
        } catch (final Exception e) {
            exception = e;
            mLogger.severe("run", RareDataFetchingTask.class, "Failed to complete " + mTaskName, e);
        } finally {
            stopBackgroundJob(id.get(), exception);
        }
    }

    protected abstract void runTask() throws Exception;

    private Optional<Long> startBackgroundJob() {
        try {
            return Optional.of(mDatabase.startBackgroundJob(mTaskName));
        } catch (final Exception e) {
            mLogger.severe("startBackgroundJob", RareDataFetchingTask.class, "Failed to init " + mTaskName, e);
            return Optional.empty();
        }
    }

    private void stopBackgroundJob(final long backgroundId, final Exception exception) {
        try {
            mDatabase.stopBackgroundJob(backgroundId, exception);
        } catch (final Exception e) {
            mLogger.severe("stopBackgroundJob", RareDataFetchingTask.class, "Failed to finish " + mTaskName, e);
        }
    }
}