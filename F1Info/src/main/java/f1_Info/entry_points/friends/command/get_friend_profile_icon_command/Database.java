package f1_Info.entry_points.friends.command.get_friend_profile_icon_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.user.commands.profile_picture_commands.get_user_profile_picture_command.GetUserProfilePictureQueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component(value = "GetFriendProfileIconDatabase}")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public byte[] getFriendProfileIcon(final long userId) throws SQLException {
        return executeBasicQuery(new GetUserProfilePictureQueryData(userId));
    }
}