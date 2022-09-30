package f1_Info.services.bell_notification_send_out_service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BellNotificationIcon {
    HAPPY_SMILEY("happy-smiley"),
    PERSON_CIRCLE_QUESTION("person-circle-question");

    private final String mCode;
}
