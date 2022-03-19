package f1_Info;

import f1_Info.background.DataFetchingTask;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class F1InfoApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(F1InfoApplication.class, args);
	}

	@Autowired
	private DataFetchingTask mDataFetchingTask;

	@Autowired
	private Logger mLogger;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(F1InfoApplication.class);
	}

	@Scheduled(cron = "*/5 * * * * *")
	public void runDataFetchingTask() {
		mLogger.logInfo("runDataFetchingTask", F1InfoApplication.class, "Started Data fetching task");
		mDataFetchingTask.run();
		mLogger.logInfo("runDataFetchingTask", F1InfoApplication.class, "Finished Data fetching task");
	}
}
