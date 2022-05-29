package common.logger;

import common.configuration.Configuration;
import common.email.EmailSendOutParameters;
import common.email.EmailService;
import common.email.EmailType;
import common.wrappers.ThreadScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class Logger implements Runnable {
    private static final String EMAIL_LOGGING_SUBJECT = "F1Info Server System Alert: SEVERE - %s";
    private static final String INFO_TEMPLATE = "%s in method: %s in class: %s";
    private static final String ERROR_TEMPLATE = "%s in method: %s in class: %s with exception: %s with the stacktrace: ";

    private final org.slf4j.Logger mLogger;
    private final EmailService mEmailService;
    private final Configuration mConfiguration;
    private final ThreadScheduler mThreadScheduler;

    static final List<String> EMAIL_LOGGING_LIST = Collections.synchronizedList(new LinkedList<>());

    @Autowired
    public Logger(
        final LoggerFactory mLoggerFactory,
        @Lazy final EmailService emailService,
        final Configuration configuration,
        final ThreadScheduler threadScheduler
    ) {
        mLogger = mLoggerFactory.getLogger();
        mEmailService = emailService;
        mConfiguration = configuration;
        mThreadScheduler = threadScheduler;
        sendOutStashedSevereEmailsEveryMinute();
    }

    public <T> void info(final String methodName, final Class<T> classType, final String message) {
        if (mLogger.isInfoEnabled()) {
            mLogger.info(String.format(INFO_TEMPLATE, message, methodName, classType.getName()));
        }
    }

    public <T> void warning(final String methodName, final Class<T> classType, final String message) {
        if (mLogger.isWarnEnabled()) {
            mLogger.warn(String.format(INFO_TEMPLATE, message, methodName, classType.getName()));
        }
    }

    public <T> void severe(final String methodName, final Class<T> classType, final String message, final Exception exception) {
        if (mLogger.isErrorEnabled()) {
            mLogger.error(String.format(ERROR_TEMPLATE, message, methodName, classType.getName(), exception), exception);

            if (!mConfiguration.getRules().getIsMock()) {
                EMAIL_LOGGING_LIST.add(formatSevereEmailLog(methodName, classType, message, exception));
            }
        }
    }

    @Override
    public void run() {
        sendLoggingEmails();
    }

    @PreDestroy
    void terminate() {
        mThreadScheduler.terminate();
    }

    private void sendOutStashedSevereEmailsEveryMinute() {
        mThreadScheduler.start(this, 1, TimeUnit.MINUTES);
    }

    private <T> String formatSevereEmailLog(final String methodName, final Class<T> classType, final String message, final Exception exception) {
        return String.format(
            LoggerEmailTemplates.ENTRY,
            EMAIL_LOGGING_LIST.size() + 1,
            methodName,
            classType.getName(),
            message,
            exception.getMessage()
        );
    }

    private void sendLoggingEmails() {
        if (EMAIL_LOGGING_LIST.isEmpty()) {
            return;
        }

        final List<String> logContent = new ArrayList<>(EMAIL_LOGGING_LIST);
        EMAIL_LOGGING_LIST.clear();

        mEmailService.sendEmail(new EmailSendOutParameters(
            mConfiguration.getRules().getLoggingEmail(),
            String.format(EMAIL_LOGGING_SUBJECT, Timestamp.from(Instant.now())),
            String.format(LoggerEmailTemplates.BASE, logContent.size(), String.join("", logContent)),
            EmailType.SEVERE_LOGGING
        ));
    }
}
