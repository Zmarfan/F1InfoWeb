package f1_Info.entry_points.friends.command.block_user_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.badRequest;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class BlockUserCommand implements Command {
    long mUserId;
    long mBlockUserId;
    Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        if (!mDatabase.canBlockUser(mUserId, mBlockUserId)) {
            return badRequest();
        }

        mDatabase.blockUser(mUserId, mBlockUserId);
        return ok();
    }
}
