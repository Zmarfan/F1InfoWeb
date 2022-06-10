package f1_Info.entry_points.authentication.services.token_service;

import common.constants.email.Email;
import lombok.Value;

@Value
public class UserInformation {
    long mUserId;
    Email mEmail;
    TokenStatusType mStatusType;
}
