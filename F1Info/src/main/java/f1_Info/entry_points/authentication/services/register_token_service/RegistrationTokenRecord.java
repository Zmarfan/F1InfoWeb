package f1_Info.entry_points.authentication.services.register_token_service;

import common.constants.email.Email;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class RegistrationTokenRecord {
    long mUserId;
    Email mEmail;
    LocalDateTime mCreationTime;
}
