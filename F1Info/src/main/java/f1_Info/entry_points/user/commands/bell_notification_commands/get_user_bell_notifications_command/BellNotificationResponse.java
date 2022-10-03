package f1_Info.entry_points.user.commands.bell_notification_commands.get_user_bell_notifications_command;

import common.utils.ListUtils;
import f1_Info.services.bell_notification_send_out_service.BellNotificationClickType;
import f1_Info.services.bell_notification_send_out_service.BellNotificationIcon;
import lombok.Value;

import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Value
public class BellNotificationResponse {
    long mNotificationId;
    BellNotificationIcon mIconType;
    BellNotificationClickType mClickType;
    String mTranslationKey;
    Map<String, String> mParameters;
    boolean mOpened;

    public BellNotificationResponse(final BellNotificationRecord bellNotificationRecord) {
        mNotificationId = bellNotificationRecord.getNotificationId();
        mIconType = BellNotificationIcon.fromString(bellNotificationRecord.getIconType());
        mClickType = BellNotificationClickType.fromString(bellNotificationRecord.getClickType());
        mTranslationKey = bellNotificationRecord.getTranslationKey();
        mParameters = bellNotificationRecord.getParameters() != null ? extractParameters(bellNotificationRecord.getParameters()) : null;
        mOpened = bellNotificationRecord.getOpened();
    }

    private Map<String, String> extractParameters(final String parameters) {
        return ListUtils.stringToList(parameters, ",", Function.identity())
            .stream()
            .collect(toMap(p -> p.substring(0, p.indexOf(":")), p -> p.substring(p.indexOf(":") + 1)));
    }
}
