package f1_Info.entry_points.friends;

import f1_Info.entry_points.friends.command.get_friends_info_command.GetFriendsInfoCommand;
import f1_Info.entry_points.helper.EndpointHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/info")
    public ResponseEntity<?> getFriendsInfo() {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new GetFriendsInfoCommand(userId, mFriendCodeHandler));
    }
}
