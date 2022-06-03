package common.helpers;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DateFactory {
    public LocalDate now() {
        return LocalDate.now();
    }

    public LocalDateTime nowTime() {
        return LocalDateTime.now();
    }
}
