package f1_Info.entry_points.authentication.services.token_service;

import common.constants.email.Email;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class TokenRecord {
    long mUserId;
    boolean mEnabled;
    Email mEmail;
    String mPassword;
    LocalDateTime mCreationTime;
}
