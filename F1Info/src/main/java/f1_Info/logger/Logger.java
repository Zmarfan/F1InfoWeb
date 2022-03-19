package f1_Info.logger;

import org.springframework.stereotype.Component;

@Component
public class Logger {
    private static final String INFO_TEMPLATE = "%s in method: %s in class: %s";
    private static final String ERROR_TEMPLATE = "%s in method: %s in class: %s with exception: %s with the stacktrace: ";
    private static final String STACKTRACE_TEMPLATE = "Stack trace:\n%s";

    // TODO: Make proper logging
    public <T> void logInfo(final String methodName, final Class<T> classType, final String message) {
        System.out.println(String.format(INFO_TEMPLATE, message, methodName, classType.getName()));
    }

    // TODO: Make proper logging
    public <T> void logError(final String methodName, final Class<T> classType, final String message, final Exception exception) {
        System.out.println(String.format(ERROR_TEMPLATE, message, methodName, classType.getName(), exception));
        exception.printStackTrace();
    }
}
