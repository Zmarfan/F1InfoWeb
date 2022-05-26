package f1_Info.entry_points;

import org.springframework.http.ResponseEntity;

public interface Command {
    ResponseEntity<?> execute() throws Exception;
    String getAction();
}
