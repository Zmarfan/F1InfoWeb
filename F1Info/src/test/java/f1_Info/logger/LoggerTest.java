package f1_Info.logger;

import f1_Info.configuration.Configuration;
import f1_Info.configuration.ConfigurationRulesBuilder;
import f1_Info.helpers.email.EmailSendOutParameters;
import f1_Info.helpers.email.EmailService;
import f1_Info.wrappers.ThreadScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoggerTest {

    @Mock
    org.slf4j.Logger mF1Logger;

    @Mock
    LoggerFactory mLoggerFactory;

    @Mock
    EmailService mEmailService;

    @Mock
    Configuration mConfiguration;

    @Mock
    ThreadScheduler mThreadScheduler;

    @Test
    void should_start_thread_scheduler_on_construction() {
        final Logger logger = createLogger();

        verify(mThreadScheduler).start(eq(logger), anyLong(), any(TimeUnit.class));
    }

    @Test
    void should_terminate_thread_scheduler_on_call() {
        createLogger().terminate();
        verify(mThreadScheduler).terminate();
    }

    @Test
    void should_log_info_if_it_is_enabled() {
        when(mLoggerFactory.getLogger()).thenReturn(mF1Logger);
        when(mF1Logger.isInfoEnabled()).thenReturn(true);

        createLogger().info("", LoggerTest.class, "");

        verify(mF1Logger).info(anyString());
    }

    @Test
    void should_not_log_info_if_it_is_disabled() {
        when(mLoggerFactory.getLogger()).thenReturn(mF1Logger);
        when(mF1Logger.isInfoEnabled()).thenReturn(false);

        createLogger().info("", LoggerTest.class, "");

        verify(mF1Logger, never()).info(anyString());
    }

    @Test
    void should_log_warning_if_it_is_enabled() {
        when(mLoggerFactory.getLogger()).thenReturn(mF1Logger);
        when(mF1Logger.isWarnEnabled()).thenReturn(true);

        createLogger().warning("", LoggerTest.class, "");

        verify(mF1Logger).warn(anyString());
    }

    @Test
    void should_not_log_warning_if_it_is_disabled() {
        when(mLoggerFactory.getLogger()).thenReturn(mF1Logger);
        when(mF1Logger.isWarnEnabled()).thenReturn(false);

        createLogger().warning("", LoggerTest.class, "");

        verify(mF1Logger, never()).warn(anyString());
    }

    @Test
    void should_log_severe_if_it_is_enabled() {
        when(mLoggerFactory.getLogger()).thenReturn(mF1Logger);
        when(mConfiguration.getRules()).thenReturn(ConfigurationRulesBuilder.builder(true).build());
        when(mF1Logger.isErrorEnabled()).thenReturn(true);

        createLogger().severe("", LoggerTest.class, "", new IllegalArgumentException());

        verify(mF1Logger).error(anyString(), any(IllegalArgumentException.class));
    }

    @Test
    void should_not_log_severe_if_it_is_disabled() {
        when(mLoggerFactory.getLogger()).thenReturn(mF1Logger);
        when(mF1Logger.isErrorEnabled()).thenReturn(false);

        createLogger().severe("", LoggerTest.class, "", new IllegalArgumentException());

        verify(mF1Logger, never()).error(anyString(), any(IllegalArgumentException.class));
    }

    @Test
    void should_not_add_log_entry_in_email_list_if_running_mock_configuration_when_logging_severe() {
        when(mLoggerFactory.getLogger()).thenReturn(mF1Logger);
        when(mConfiguration.getRules()).thenReturn(ConfigurationRulesBuilder.builder(true).build());
        when(mF1Logger.isErrorEnabled()).thenReturn(true);

        createLogger().severe("", LoggerTest.class, "", new IllegalArgumentException());

        assertTrue(Logger.EMAIL_LOGGING_LIST.isEmpty());
    }

    @Test
    void should_add_log_entry_in_email_list_if_running_production_configuration_when_logging_severe() {
        when(mLoggerFactory.getLogger()).thenReturn(mF1Logger);
        when(mConfiguration.getRules()).thenReturn(ConfigurationRulesBuilder.builder(false).build());
        when(mF1Logger.isErrorEnabled()).thenReturn(true);

        createLogger().severe("", LoggerTest.class, "", new IllegalArgumentException());

        assertEquals(1, Logger.EMAIL_LOGGING_LIST.size());
    }

    @Test
    void should_clear_email_logging_list_when_sending_out_emails_list_is_non_empty() {
        when(mLoggerFactory.getLogger()).thenReturn(mF1Logger);
        when(mConfiguration.getRules()).thenReturn(ConfigurationRulesBuilder.builder(false).build());
        when(mF1Logger.isErrorEnabled()).thenReturn(true);

        final Logger logger = createLogger();
        logger.severe("", LoggerTest.class, "", new IllegalArgumentException());
        logger.run();

        assertTrue(Logger.EMAIL_LOGGING_LIST.isEmpty());
    }

    @Test
    void should_not_send_out_severe_email_if_logging_list_is_empty() {
        createLogger().run();

        verify(mEmailService, never()).sendEmail(any(EmailSendOutParameters.class));
    }

    @Test
    void should_send_out_severe_email_if_logging_list_is_non_empty() {
        when(mLoggerFactory.getLogger()).thenReturn(mF1Logger);
        when(mConfiguration.getRules()).thenReturn(ConfigurationRulesBuilder.builder(false).build());
        when(mF1Logger.isErrorEnabled()).thenReturn(true);

        final Logger logger = createLogger();
        logger.severe("", LoggerTest.class, "", new IllegalArgumentException());
        logger.run();

        verify(mEmailService).sendEmail(any(EmailSendOutParameters.class));
    }

    private Logger createLogger() {
        return new Logger(mLoggerFactory, mEmailService, mConfiguration, mThreadScheduler);
    }
}
