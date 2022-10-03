package f1_Info.services.bell_notification_send_out_service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum BellNotificationIcon {
    HAPPY_SMILEY("happy-smiley"),
    PERSON_CIRCLE_QUESTION("person-circle-question");

    private final String mCode;

    public static BellNotificationIcon fromString(final String code) {
        return Arrays.stream(values())
            .filter(clickType -> Objects.equals(code, clickType.getCode()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the code: %s to a valid BellNotificationIcon", code)));
    }
}
