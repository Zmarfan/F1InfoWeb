package f1_Info.entry_points.authentication.services.token_service;

import lombok.Value;

@Value
public class TokenUserErrorResponse {
    TokenStatusType mErrorType;
}
