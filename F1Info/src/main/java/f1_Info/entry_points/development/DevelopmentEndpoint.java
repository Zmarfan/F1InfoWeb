package f1_Info.entry_points.development;

import f1_Info.entry_points.development.commands.feedback_commands.create_feedback_item_command.CreateFeedbackItemCommand;
import f1_Info.entry_points.development.commands.feedback_commands.create_feedback_item_command.CreateFeedbackItemRequestBody;
import f1_Info.entry_points.development.commands.feedback_commands.delete_feedback_item_command.DeleteFeedbackItemCommand;
import f1_Info.entry_points.development.commands.feedback_commands.get_feedback_items_command.GetFeedbackItemsCommand;
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

    @GetMapping("/get-change-log-items")
    public ResponseEntity<?> getChangeLogItems() {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new GetChangeLogItemsCommand(mDatabase));
    }

    @GetMapping("/get-feedback-items")
    public ResponseEntity<?> getFeedbackItems() {
        return mEndpointHelper.authorizeAndRun(mHttpServletRequest, userId -> new GetFeedbackItemsCommand(userId, mFeedbackDatabase));
    }

    @PostMapping("/create-feedback-item")
    public ResponseEntity<?> createFeedbackItem(@RequestBody final CreateFeedbackItemRequestBody requestBody) {
        if (requestBody == null || requestBody.getText().length() < 10 || requestBody.getText().length() > 255) {
            return badRequest();
        }

        return mEndpointHelper.authorizeAndRun(mHttpServletRequest, userId -> new CreateFeedbackItemCommand(userId, requestBody.getText(), mFeedbackDatabase));
    }

    @PostMapping("/delete-feedback-item/{itemId}")
    public ResponseEntity<?> deleteFeedbackItem(
        @PathVariable("itemId") final Long itemId
    ) {
        if (itemId == null) {
            return badRequest();
        }

        return mEndpointHelper.authorizeAndRun(mHttpServletRequest, userId -> new DeleteFeedbackItemCommand(userId, itemId, mFeedbackDatabase));
    }
}