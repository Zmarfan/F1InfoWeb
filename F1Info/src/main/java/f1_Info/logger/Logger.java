package f1_Info.logger;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Logger {
    private static final String INFO_TEMPLATE = "%s: %s in method: %s in class: %s";
    private static final String ERROR_TEMPLATE = "%s: %s in method: %s in class: %s with exception: %s with the stacktrace: ";

    // TODO: Make proper logging
    public <T> void logInfo(final String methodName, final Class<T> classType, final String message) {
        System.out.println(String.format(INFO_TEMPLATE, getFormattedTimestamp(), message, methodName, classType.getName()));
    }

    // TODO: Make proper logging
    public <T> void logError(final String methodName, final Class<T> classType, final String message, final Exception exception) {
        System.out.println(String.format(ERROR_TEMPLATE, getFormattedTimestamp(), message, methodName, classType.getName(), exception));
        exception.printStackTrace();
    }

    private String getFormattedTimestamp() {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        final LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }
}
