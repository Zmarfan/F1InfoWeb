package f1_Info.helpers.email;

import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import java.util.Optional;

@Component
class EmailDispatcher {
    public Optional<MessagingException> dispatch(final Message message) {
        try {
            Transport.send(message);
        } catch (final MessagingException e) {
            return Optional.of(e);
        }
        return Optional.empty();
    }
}
