package common.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailType {
    SEVERE_LOGGING(1),
    REGISTRATION_VERIFICATION(2),
    RESET_PASSWORD(3);

    private final int mId;
}
