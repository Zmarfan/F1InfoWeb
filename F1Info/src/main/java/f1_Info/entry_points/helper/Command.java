package f1_Info.entry_points.helper;

import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

public interface Command {
    ResponseEntity<?> execute() throws SQLException;
}
