package f1_Info;

import f1_Info.background.ergast_tasks.fetch_circuits_task.FetchCircuitsTask;
import f1_Info.background.ergast_tasks.fetch_constructor_standings_task.FetchConstructorStandingsTask;
import f1_Info.background.ergast_tasks.fetch_constructors_task.FetchConstructorsTask;
import f1_Info.background.ergast_tasks.fetch_driver_standings_task.FetchDriverStandingsTask;
import f1_Info.background.ergast_tasks.fetch_drivers_task.FetchDriversTask;
import f1_Info.background.ergast_tasks.fetch_finish_status_task.FetchFinishStatusTask;
import f1_Info.background.ergast_tasks.fetch_lap_times_task.FetchLapTimesTask;
import f1_Info.background.ergast_tasks.fetch_pitstops_task.FetchPitStopsTask;
import f1_Info.background.ergast_tasks.fetch_qualifying_results_task.FetchQualifyingResultsTask;
import f1_Info.background.ergast_tasks.fetch_races_task.FetchRacesTask;
import f1_Info.background.ergast_tasks.fetch_results_tasks.fetch_race_results_task.FetchRaceResultsTask;
import f1_Info.background.ergast_tasks.fetch_results_tasks.fetch_sprint_results_task.FetchSprintResultsTask;
import f1_Info.background.ergast_tasks.fetch_seasons_task.FetchSeasonsTask;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication(scanBasePackages = { "f1_Info", "common", "database" })
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
	private final FetchDriverStandingsTask mFetchDriverStandingsTask;
	private final FetchConstructorStandingsTask mFetchConstructorStandingsTask;
	private final FetchSprintResultsTask mFetchSprintResultsTask;
	private final FetchRaceResultsTask mFetchRaceResultsTask;
	private final FetchQualifyingResultsTask mFetchQualifyingResultsTask;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(F1InfoApplication.class);
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchFinishStatusTask() {
		mFetchFinishStatusTask.run();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchCircuitsTask() {
		mFetchCircuitsTask.run();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchDriversTask() {
		mFetchDriversTask.run();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchSeasonsTask() {
		mFetchSeasonsTask.run();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchConstructorsTask() {
		mFetchConstructorsTask.run();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchRacesTask() {
		mFetchRacesTask.run();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchPitStopsTask() {
		mFetchPitStopsTask.run();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchLapTimesTask() {
		mFetchLapTimesTask.run();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchDriverStandingsTask() {
		mFetchDriverStandingsTask.run();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchConstructorStandingsTask() {
		mFetchConstructorStandingsTask.run();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchSprintResultsTask() {
		mFetchSprintResultsTask.run();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchRaceResultsTask() {
		mFetchRaceResultsTask.run();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runFetchQualifyingResultsTask() {
		mFetchQualifyingResultsTask.run();
	}
}
