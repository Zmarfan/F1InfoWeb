package f1_Info.entry_points.development;

import f1_Info.entry_points.development.commands.feedback_commands.create_feedback_item_command.CreateFeedbackItemCommand;
import f1_Info.entry_points.development.commands.feedback_commands.create_feedback_item_command.CreateFeedbackItemRequestBody;
import f1_Info.entry_points.development.commands.feedback_commands.delete_feedback_item_command.DeleteFeedbackItemCommand;
import f1_Info.entry_points.development.commands.feedback_commands.get_feedback_items_command.GetFeedbackItemsCommand;
import f1_Info.entry_points.development.commands.feedback_commands.like_feedback_item_command.LikeFeedbackCommand;
import f1_Info.entry_points.development.commands.feedback_commands.remove_feedback_item_like_command.RemoveFeedbackItemLikeCommand;
import f1_Info.entry_points.development.commands.get_change_log_items_command.Database;
import f1_Info.entry_points.development.commands.get_change_log_items_command.GetChangeLogItemsCommand;
import f1_Info.entry_points.helper.EndpointHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static f1_Info.configuration.web.ResponseUtil.badRequest;

@RestController
@RequestMapping("/Development")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class DevelopmentEndpoint {
    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;
    private final Database mDatabase;
    private final f1_Info.entry_points.development.commands.feedback_commands.Database mFeedbackDatabase;

    @GetMapping("/change-log-items")
    public ResponseEntity<?> getChangeLogItems() {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new GetChangeLogItemsCommand(mDatabase));
    }

    @GetMapping("/feedback-items")
    public ResponseEntity<?> getFeedbackItems() {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new GetFeedbackItemsCommand(userId, mFeedbackDatabase));
    }

    @PutMapping("/feedback-item")
    public ResponseEntity<?> createFeedbackItem(@RequestBody final CreateFeedbackItemRequestBody requestBody) {
        if (requestBody == null || requestBody.getText().isBlank() || requestBody.getText().length() > 255) {
            return badRequest();
        }

        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new CreateFeedbackItemCommand(userId, requestBody.getText(), mFeedbackDatabase));
    }

    @DeleteMapping("/feedback-item/{itemId}")
    public ResponseEntity<?> deleteFeedbackItem(
        @PathVariable("itemId") final Long itemId
    ) {
        if (itemId == null) {
            return badRequest();
        }

        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new DeleteFeedbackItemCommand(userId, itemId, mFeedbackDatabase));
    }

    @PutMapping("/feedback-item-like/{itemId}")
    public ResponseEntity<?> likeFeedbackItem(
        @PathVariable("itemId") final Long itemId
    ) {
        if (itemId == null) {
            return badRequest();
        }

        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new LikeFeedbackCommand(userId, itemId, mFeedbackDatabase));
    }

    @DeleteMapping("/feedback-item-like/{itemId}")
    public ResponseEntity<?> removeFeedbackItemLike(
        @PathVariable("itemId") final Long itemId
    ) {
        if (itemId == null) {
            return badRequest();
        }

        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new RemoveFeedbackItemLikeCommand(userId, itemId, mFeedbackDatabase));
    }
}