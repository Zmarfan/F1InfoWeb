package database.permissions;

public class UserPermissions {
    public static boolean userIsUser(final long isUserId, final long checkUserId) {
        return isUserId == checkUserId;
    }
}
