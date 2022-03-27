package f1_Info;

import f1_Info.background.fetch_constructors_task.FetchConstructorsTask;
import f1_Info.background.fetch_drivers_task.FetchDriversTask;
import f1_Info.background.fetch_races_task.FetchRacesTask;
import f1_Info.background.fetch_seasons_task.FetchSeasonsTask;
import f1_Info.background.fetch_circuits_task.FetchCircuitsTask;
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
	private FetchCircuitsTask mFetchCircuitsTask;

	@Autowired
	private FetchDriversTask mFetchDriversTask;

	@Autowired
	private FetchSeasonsTask mFetchSeasonsTask;

	@Autowired
	private FetchRacesTask mFetchRacesTask;

	@Autowired
	private FetchConstructorsTask mFetchConstructorsTask;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(F1InfoApplication.class);
	}

	@Scheduled(cron = "0 * * * * *")
	public void runMonthlyTasks() {
		mFetchCircuitsTask.run();
	}

	@Scheduled(cron = "1 * * * * *")
	public void runFetchDriversTask() {
		mFetchDriversTask.run();
	}

	@Scheduled(cron = "2 * * * * *")
	public void runFetchSeasonsTask() {
		mFetchSeasonsTask.run();
	}

	@Scheduled(cron = "3 * * * * *")
	public void runFetchConstructorsTask() {
		mFetchConstructorsTask.run();
	}

	@Scheduled(cron = "4 * * * * *")
	public void runFetchRacesTask() {
		mFetchRacesTask.run();
	}
}
