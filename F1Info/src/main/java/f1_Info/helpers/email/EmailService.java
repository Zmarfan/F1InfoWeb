package f1_Info.helpers.email;

import f1_Info.configuration.Configuration;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;

@Component
public class EmailService {
    private final Logger mLogger;
    private final Configuration mConfiguration;

    private final Session mSession;

    @Autowired
    public EmailService(
        final Configuration configuration,
        final Logger logger
    ) {
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

    private Properties createProperties() {
        final Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        return properties;
    }

    private Session createSession(Configuration configuration) {
        return Session.getInstance(createProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(configuration.getRules().getEmail().read(), configuration.getRules().getEmailPassword());
            }
        });
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
        try {
            Transport.send(message);
            mLogger.info("dispatch", this.getClass(), String.format(
                "Successfully sent email to %s with subject: %s for email type: %d",
                parameters.getRecipient().read(),
                parameters.getSubject(),
                parameters.getType().getId()
            ));
            return true;
        } catch (final MessagingException e) {
            mLogger.severe("dispatch", this.getClass(), String.format(
                "Failed to send email to %s with subject: %s for email type: %d",
                parameters.getRecipient().read(),
                parameters.getSubject(),
                parameters.getType().getId()
            ), e);
            return false;
        }
    }
}
