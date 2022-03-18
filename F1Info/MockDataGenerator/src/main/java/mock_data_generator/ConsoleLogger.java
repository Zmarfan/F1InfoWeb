package mock_data_generator;

public class ConsoleLogger {
    public static void logInfo(final String text) {
        log(text, ConsoleColor.INFO);
    }

    public static void logFile(final String text) {
        log(text, ConsoleColor.FILE);
    }

    public static void logError(final String text) {
        log(text, ConsoleColor.ERROR);
    }

    public static void logSuccess(final String text) {
        log(text, ConsoleColor.SUCCESS);
    }

    private static void log(final String text, final ConsoleColor color) {
        System.out.println(color.getCode() + text + ConsoleColor.RESET.getCode());
    }
}
