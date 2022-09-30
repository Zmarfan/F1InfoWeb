package f1_Info.entry_points.friends;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum FriendStatus {
    FRIENDS("friend"),
    PENDING("pending"),
    NOT_FRIENDS("not_friend"),
    BLOCKED("blocked");

    private final String mType;

    public static FriendStatus fromString(final String type) {
        return Arrays.stream(values())
            .filter(friendStatus -> friendStatus.getType().equals(type))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the type: %s to a valid Friend status", type)));
    }
}
