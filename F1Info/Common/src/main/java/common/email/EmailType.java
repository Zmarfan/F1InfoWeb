package common.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailType {
    SEVERE_LOGGING(1);

    private final int mId;
}