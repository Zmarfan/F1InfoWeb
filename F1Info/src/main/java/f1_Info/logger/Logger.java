package f1_Info.logger;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Logger {
    private static final String INFO_TEMPLATE = "%s in method: %s in class: %s";
    private static final String ERROR_TEMPLATE = "%s in method: %s in class: %s with exception: %s with the stacktrace: ";

    private static final org.slf4j.Logger F_1_LOGGER = LoggerFactory.getLogger("f1Logger");

    public <T> void info(final String methodName, final Class<T> classType, final String message) {
        if (F_1_LOGGER.isInfoEnabled()) {
            F_1_LOGGER.info(String.format(INFO_TEMPLATE, message, methodName, classType.getName()));
        }
    }

    public <T> void warning(final String methodName, final Class<T> classType, final String message) {
        if (F_1_LOGGER.isWarnEnabled()) {
            F_1_LOGGER.warn(String.format(INFO_TEMPLATE, message, methodName, classType.getName()));
        }
    }

    public <T> void severe(final String methodName, final Class<T> classType, final String message, final Exception exception) {
        if (F_1_LOGGER.isErrorEnabled()) {
            F_1_LOGGER.error(String.format(ERROR_TEMPLATE, message, methodName, classType.getName(), exception), exception);
        }
    }
}
