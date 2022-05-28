package f1_Info.logger;

import f1_Info.configuration.Configuration;
import f1_Info.helpers.email.EmailSendOutParameters;
import f1_Info.helpers.email.EmailService;
import f1_Info.helpers.email.EmailType;
import f1_Info.wrappers.ThreadScheduler;
import org.slf4j.LoggerFactory;
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

    private final EmailService mEmailService;
    private final Configuration mConfiguration;
    private final ThreadScheduler mThreadScheduler;

    private static final List<String> EMAIL_LOGGING_LIST = Collections.synchronizedList(new LinkedList<>());
    private static final org.slf4j.Logger F_1_LOGGER = LoggerFactory.getLogger("f1Logger");

    @Autowired
    public Logger(
        @Lazy final EmailService emailService,
        final Configuration configuration,
        final ThreadScheduler threadScheduler
    ) {
        mEmailService = emailService;
        mConfiguration = configuration;
        mThreadScheduler = threadScheduler;

        mThreadScheduler.start(this, 1, TimeUnit.MINUTES);
    }

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
    private void terminate() {
        mThreadScheduler.terminate();
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
