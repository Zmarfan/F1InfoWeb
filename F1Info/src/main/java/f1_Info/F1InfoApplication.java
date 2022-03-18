package f1_Info;

import f1_Info.ergast.ErgastProxy;
import f1_Info.logger.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class F1InfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(F1InfoApplication.class, args);

		final ErgastProxy ergastProxy = new ErgastProxy(new Logger());
		ergastProxy.fetchAllConstructors();
	}
}
