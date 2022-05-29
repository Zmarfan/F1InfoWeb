package f1_Info.background;

import common.logger.Logger;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public abstract class TaskWrapper {
    protected final Logger mLogger;
    private final TaskDatabase mTaskDatabase;

    public void run() {
        final Optional<Long> id = startBackgroundJob();
        if (id.isEmpty()) {
            return;
        }

        Exception exception = null;
        try {
            mLogger.info("run", TaskWrapper.class, String.format("Started the task: %s", getTaskType().getName()));
            runTask();
            mLogger.info("run", TaskWrapper.class, String.format("Finished the task: %s with no errors", getTaskType().getName()));
        } catch (final Exception e) {
            exception = e;
            mLogger.severe("run", TaskWrapper.class, "Failed to complete " + getTaskType().getName(), e);
        } finally {
            stopBackgroundJob(id.get(), exception);
        }
    }

    protected abstract void runTask() throws Exception;
    protected abstract Tasks getTaskType();

    private Optional<Long> startBackgroundJob() {
        try {
            return Optional.of(mTaskDatabase.startBackgroundJob(getTaskType()));
        } catch (final Exception e) {
            mLogger.severe("startBackgroundJob", TaskWrapper.class, "Failed to init " + getTaskType().getName(), e);
            return Optional.empty();
        }
    }

    private void stopBackgroundJob(final long backgroundId, final Exception exception) {
        try {
            mTaskDatabase.stopBackgroundJob(backgroundId, exception);
        } catch (final Exception e) {
            mLogger.severe("stopBackgroundJob", TaskWrapper.class, "Failed to finish " + getTaskType().getName(), e);
        }
    }
}
