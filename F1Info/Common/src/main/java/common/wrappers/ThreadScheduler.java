package common.wrappers;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadScheduler {
    private final ScheduledExecutorService mExecutorService;

    public ThreadScheduler() {
        mExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void start(final Runnable runnable, final long period, final TimeUnit timeUnit) {
        mExecutorService.scheduleAtFixedRate(runnable, period, period, timeUnit);
    }

    @PreDestroy
    public void terminate() {
        mExecutorService.shutdown();
    }
}