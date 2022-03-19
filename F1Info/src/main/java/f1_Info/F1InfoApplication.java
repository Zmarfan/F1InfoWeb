package f1_Info;

import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.ConstructorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class F1InfoApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(F1InfoApplication.class, args);
	}

	@Autowired
	private ErgastProxy mErgastProxy;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(F1InfoApplication.class);
	}

	@PostConstruct
	public void listen() {
		final List<ConstructorData> constructors = mErgastProxy.fetchAllConstructors();
		final List<String> insertStatements = constructors.stream().map(this::constructorToInsertStatement).toList();
		System.out.println(String.join("\n", insertStatements));
	}

	private String constructorToInsertStatement(final ConstructorData constructorData) {
		return String.format(
			"insert into constructors (constructor_identifier, name, country_code, wikipedia_page) values ('%s', '%s', '%s', '%s');",
			constructorData.getConstructorIdentifier(),
			constructorData.getName(),
			constructorData.getCountry().getCode(),
			constructorData.getWikipediaUrl()
		);
	}
}
