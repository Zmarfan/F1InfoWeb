package f1_Info.communication;

import common.configuration.Configuration;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class ClientUriFactory {
    private final Configuration mConfiguration;

    public String createResetPasswordUri(final UUID token) {
        return String.format("%s/reset-password?token=%s", mConfiguration.getRules().getClientDomain(), token.toString());
    }

    public String createSignUpUri(final UUID token) {
        return String.format("%s/sign-up?type=3&token=%s", mConfiguration.getRules().getClientDomain(), token.toString());
    }
}
