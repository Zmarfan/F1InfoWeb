package f1_Info;

import f1_Info.background.ConstructorDataFetchingTask;
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
	private ConstructorDataFetchingTask mConstructorDataFetchingTask;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(F1InfoApplication.class);
	}

	@Scheduled(cron = "*/45 * * * * *")
	public void runConstructorDataFetchingTask() {
		mConstructorDataFetchingTask.run();
	}
}
