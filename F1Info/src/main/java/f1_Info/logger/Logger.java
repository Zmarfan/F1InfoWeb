package f1_Info.logger;

public class Logger {
    private static final String ERROR_TEMPLATE = "%s: With exception: %s";

    // TODO: Make proper logging
    public void logInfo(final String message) {
        System.out.println(message);
    }

    // TODO: Make proper logging
    public void logError(final String message, final Exception exception) {
        System.out.println(String.format(ERROR_TEMPLATE, message, exception));
    }
}
