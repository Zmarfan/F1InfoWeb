package f1_Info;

import f1_Info.background.ergast_tasks.fetch_circuits_task.FetchCircuitsTask;
import f1_Info.background.ergast_tasks.fetch_constructors_task.FetchConstructorsTask;
import f1_Info.background.ergast_tasks.fetch_drivers_task.FetchDriversTask;
import f1_Info.background.ergast_tasks.fetch_finish_status_task.FetchFinishStatusTask;
import f1_Info.background.ergast_tasks.fetch_lap_times_task.FetchLapTimesTask;
import f1_Info.background.ergast_tasks.fetch_pitstops_task.FetchPitStopsTask;
import f1_Info.background.ergast_tasks.fetch_races_task.FetchRacesTask;
import f1_Info.background.ergast_tasks.fetch_seasons_task.FetchSeasonsTask;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class F1InfoApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(F1InfoApplication.class, args);
	}

	private final FetchFinishStatusTask mFetchFinishStatusTask;
	private final FetchCircuitsTask mFetchCircuitsTask;
	private final FetchDriversTask mFetchDriversTask;
	private final FetchSeasonsTask mFetchSeasonsTask;
	private final FetchRacesTask mFetchRacesTask;
	private final FetchConstructorsTask mFetchConstructorsTask;
	private final FetchPitStopsTask mFetchPitStopsTask;
	private final FetchLapTimesTask mFetchLapTimesTask;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(F1InfoApplication.class);
	}

	@Scheduled(cron = "*/5 * * * * *")
	public void runFetchFinishStatusTask() {
		mFetchFinishStatusTask.run();
	}

	@Scheduled(cron = "*/5 * * * * *")
	public void runFetchCircuitsTask() {
		mFetchCircuitsTask.run();
	}

	@Scheduled(cron = "*/5 * * * * *")
	public void runFetchDriversTask() {
		mFetchDriversTask.run();
	}

	@Scheduled(cron = "*/5 * * * * *")
	public void runFetchSeasonsTask() {
		mFetchSeasonsTask.run();
	}

	@Scheduled(cron = "*/5 * * * * *")
	public void runFetchConstructorsTask() {
		mFetchConstructorsTask.run();
	}

	@Scheduled(cron = "*/5 * * * * *")
	public void runFetchRacesTask() {
		mFetchRacesTask.run();
	}

	@Scheduled(cron = "*/5 * * * * *")
	public void runFetchPitStopsTask() {
		mFetchPitStopsTask.run();
	}

	@Scheduled(cron = "*/5 * * * * *")
	public void runFetchLapTimesTask() {
		mFetchLapTimesTask.run();
	}
}
