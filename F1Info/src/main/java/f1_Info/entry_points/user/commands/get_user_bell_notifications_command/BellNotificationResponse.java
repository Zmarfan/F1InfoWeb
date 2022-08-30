package f1_Info.entry_points.user.commands.get_user_bell_notifications_command;

import common.utils.ListUtils;
import lombok.Value;

import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Value
public class BellNotificationResponse {
    long mNotificationId;
    String mIconType;
    String mTranslationKey;
    Map<String, String> mParameters;
    boolean mOpened;

    public BellNotificationResponse(final BellNotificationRecord bellNotificationRecord) {
        mNotificationId = bellNotificationRecord.getNotificationId();
        mIconType = bellNotificationRecord.getIconType();
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
