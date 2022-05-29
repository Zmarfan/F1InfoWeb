package common.email;

import common.configuration.Configuration;
import common.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;

@Component
public class EmailService {
    private final EmailDispatcher mEmailDispatcher;
    private final Logger mLogger;
    private final Configuration mConfiguration;

    private final Session mSession;

    @Autowired
    public EmailService(
        final EmailDispatcher emailDispatcher,
        final Configuration configuration,
        final Logger logger
    ) {
        mEmailDispatcher = emailDispatcher;
        mLogger = logger;
        mConfiguration = configuration;

        mSession = createSession(configuration);
    }

    public boolean sendEmail(final EmailSendOutParameters parameters) {
        Optional<Message> message = createMessage(parameters);
        if (message.isEmpty()) {
            return false;
        }
        return dispatch(message.get(), parameters);
    }

    private Session createSession(Configuration configuration) {
        return Session.getInstance(createProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(configuration.getRules().getEmail().read(), configuration.getRules().getEmailPassword());
            }
        });
    }

    private Properties createProperties() {
        final Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        return properties;
    }

    private Optional<Message> createMessage(final EmailSendOutParameters parameters) {
        final Message message;
        try {
            message = new MimeMessage(mSession);
            message.setFrom(new InternetAddress(mConfiguration.getRules().getEmail().read()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(parameters.getRecipient().read()));
            message.setSubject(parameters.getSubject());
            message.setContent(parameters.getHtmlMessage(), "text/html");
        } catch (final MessagingException e) {
            mLogger.severe("createMessage", this.getClass(), String.format(
                "Unable to create email message for recipient: %s with subject: %s for email type: %d",
                parameters.getRecipient().read(),
                parameters.getSubject(),
                parameters.getType().getId()
            ), e);
            return Optional.empty();
        }

        return Optional.of(message);
    }

    private boolean dispatch(final Message message, final EmailSendOutParameters parameters) {
        final Optional<MessagingException> exception = mEmailDispatcher.dispatch(message);

        if (exception.isPresent()) {
            mLogger.severe("dispatch", this.getClass(), String.format(
                "Failed to send email to %s with subject: %s for email type: %d",
                parameters.getRecipient().read(),
                parameters.getSubject(),
                parameters.getType().getId()
            ), exception.get());
        } else {
            mLogger.info("dispatch", this.getClass(), String.format(
                "Successfully sent email to %s with subject: %s for email type: %d",
                parameters.getRecipient().read(),
                parameters.getSubject(),
                parameters.getType().getId()
            ));
        }

       return exception.isEmpty();
    }
}
