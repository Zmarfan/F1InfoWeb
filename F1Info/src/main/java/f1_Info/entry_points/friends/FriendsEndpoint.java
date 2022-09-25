package f1_Info.entry_points.friends;

import f1_Info.entry_points.friends.command.get_friends_info_command.GetFriendsInfoCommand;
import f1_Info.entry_points.friends.command.search_friend_command.Database;
import f1_Info.entry_points.friends.command.search_friend_command.SearchFriendCommand;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Friends")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class FriendsEndpoint {
    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;
    private final FriendCodeHandler mFriendCodeHandler;
    private final Database mSearchDatabase;

    @GetMapping("/info")
    public ResponseEntity<?> getFriendsInfo() {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new GetFriendsInfoCommand(userId, mFriendCodeHandler));
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
}
