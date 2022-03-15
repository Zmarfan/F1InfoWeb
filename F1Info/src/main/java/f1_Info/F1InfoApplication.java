package f1_Info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class F1InfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(F1InfoApplication.class, args);

		test();
	}

	private static void test() {
		final Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/f1database", "f1User", "0Vw7LIMZ7K17");
			final Statement statement = connection.createStatement();
			statement.execute("insert into testtable (id, name) values (5, 'Bosse')");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
