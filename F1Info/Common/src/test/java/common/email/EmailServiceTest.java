package common.email;

import common.configuration.Configuration;
import common.configuration.ConfigurationRulesTestBuilder;
import common.constants.Email;
import common.logger.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    private static final EmailSendOutParameters SEND_OUT_PARAMETERS = new EmailSendOutParameters(
        new Email("test@test.com"),
        "header",
        "message",
        EmailType.SEVERE_LOGGING
    );

    @Mock
    EmailDispatcher mEmailDispatcher;

    @Mock
    Logger mLogger;

    @Mock
    Configuration mConfiguration;

    @BeforeEach
    void init() {
        when(mConfiguration.getRules()).thenReturn(ConfigurationRulesTestBuilder.builder(true).build());
    }

    @Test
    void should_return_false_when_failing_to_dispatch_email() {
        when(mEmailDispatcher.dispatch(any(Message.class))).thenReturn(Optional.of(new MessagingException()));

        assertFalse(createEmailService().sendEmail(SEND_OUT_PARAMETERS));
    }

    @Test
    void should_log_severe_when_failing_to_dispatch_email() {
        final MessagingException exception = new MessagingException("vroom");
        when(mEmailDispatcher.dispatch(any(Message.class))).thenReturn(Optional.of(exception));

        createEmailService().sendEmail(SEND_OUT_PARAMETERS);

        verify(mLogger).severe(anyString(), eq(EmailService.class), anyString(), eq(exception));
    }

    @Test
    void should_return_true_when_successfully_dispatched_an_email() {
        when(mEmailDispatcher.dispatch(any(Message.class))).thenReturn(Optional.empty());

        assertTrue(createEmailService().sendEmail(SEND_OUT_PARAMETERS));
    }

    @Test
    void should_log_info_when_successfully_dispatched_an_email() {
        when(mEmailDispatcher.dispatch(any(Message.class))).thenReturn(Optional.empty());

        createEmailService().sendEmail(SEND_OUT_PARAMETERS);

        verify(mLogger).info(anyString(), eq(EmailService.class), anyString());
    }

    private EmailService createEmailService() {
        return new EmailService(mEmailDispatcher, mConfiguration, mLogger);
    }
}
