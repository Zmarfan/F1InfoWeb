package f1_Info.entry_points.friends;

import f1_Info.entry_points.friends.command.answer_friend_request_commands.AnswerFriendRequestRequestBody;
import f1_Info.entry_points.friends.command.answer_friend_request_commands.accept.AcceptFriendRequestCommand;
import f1_Info.entry_points.friends.command.answer_friend_request_commands.decline.DeclineFriendRequestCommand;
import f1_Info.entry_points.friends.command.block_user_command.BlockUserCommand;
import f1_Info.entry_points.friends.command.block_user_command.BlockUserRequestBody;
import f1_Info.entry_points.friends.command.get_friends_info_command.GetFriendsInfoCommand;
import f1_Info.entry_points.friends.command.search_friend_command.Database;
import f1_Info.entry_points.friends.command.search_friend_command.SearchFriendCommand;
import f1_Info.entry_points.friends.command.send_friend_request_command.MakeFriendRequestBody;
import f1_Info.entry_points.friends.command.send_friend_request_command.SendFriendRequestCommand;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.services.bell_notification_send_out_service.BellNotificationSendOutService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Friends")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class FriendsEndpoint {
    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;
    private final FriendCodeHandler mFriendCodeHandler;
    private final Database mSearchDatabase;
    private final f1_Info.entry_points.friends.command.send_friend_request_command.Database mRequestDatabase;
    private final f1_Info.entry_points.friends.command.get_friends_info_command.Database mFriendInfoDatabase;
    private final f1_Info.entry_points.friends.command.answer_friend_request_commands.Database mAnswerRequestDatabase;
    private final f1_Info.entry_points.friends.command.block_user_command.Database mBlockUserDatabase;
    private final BellNotificationSendOutService mBellNotificationSendOutService;

    @GetMapping("/info")
    public ResponseEntity<?> getFriendsInfo() {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new GetFriendsInfoCommand(userId, mFriendInfoDatabase, mFriendCodeHandler));
    }

    @GetMapping("/search/{friendCode}")
    public ResponseEntity<?> searchFriend(
        @PathVariable("friendCode") final String friendCode
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (friendCode == null || friendCode.isBlank()) {
                throw new BadRequestException();
            }

            return new SearchFriendCommand(friendCode, userId, mSearchDatabase, mFriendCodeHandler);
        });
    }

    @PostMapping("/friend-request")
    public ResponseEntity<?> searchFriend(
        @RequestBody final MakeFriendRequestBody body
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (body == null || body.getFriendCode() == null || body.getFriendCode().isBlank()) {
                throw new BadRequestException();
            }

            return new SendFriendRequestCommand(body.getFriendCode(), userId, mFriendCodeHandler, mRequestDatabase, mBellNotificationSendOutService);
        });
    }

    @PostMapping("/accept-friend-request")
    public ResponseEntity<?> acceptFriendRequest(
        @RequestBody final AnswerFriendRequestRequestBody body
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (body == null) {
                throw new BadRequestException();
            }

            return new AcceptFriendRequestCommand(userId, body.getUserId(), mAnswerRequestDatabase, mBellNotificationSendOutService);
        });
    }

    @PostMapping("/decline-friend-request")
    public ResponseEntity<?> declineFriendRequest(
        @RequestBody final AnswerFriendRequestRequestBody body
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (body == null) {
                throw new BadRequestException();
            }

            return new DeclineFriendRequestCommand(userId, body.getUserId(), mAnswerRequestDatabase);
        });
    }

    @PostMapping("/block-user")
    public ResponseEntity<?> blockUser(
        @RequestBody final BlockUserRequestBody body
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (body == null) {
                throw new BadRequestException();
            }

            return new BlockUserCommand(userId, body.getUserId(), mBlockUserDatabase);
        });
    }
}
