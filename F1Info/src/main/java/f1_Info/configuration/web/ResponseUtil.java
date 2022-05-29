package f1_Info.configuration.web;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseUtil {
    public static ResponseEntity<Void> ok() {
        return ResponseEntity.ok().build();
    }

    public static <T> ResponseEntity<T> ok(final T response) {
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Void> badRequest() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<T> badRequest(final T response) {
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Void> forbidden() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    public static <T> ResponseEntity<T> forbidden(final T response) {
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<Void> conflict() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    public static <T> ResponseEntity<T> conflict(final T response) {
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    public static ResponseEntity<Void> internalServerError() {
        return ResponseEntity.internalServerError().build();
    }
}
