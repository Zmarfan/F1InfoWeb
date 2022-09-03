package f1_Info.entry_points.development;

import f1_Info.entry_points.development.manager_commands.feedback_commands.Database;
import f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_complete_command.MarkFeedbackItemAsCompleteCommand;
import f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_complete_command.MarkFeedbackItemAsCompleteLogic;
import f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_will_not_do_command.MarkFeedbackItemAsWillNotDoCommand;
import f1_Info.entry_points.helper.EndpointHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static f1_Info.configuration.web.ResponseUtil.badRequest;

@RestController
@RequestMapping("/ManagerDevelopment")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class ManagerDevelopmentEndpoint {
    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;
    private final MarkFeedbackItemAsCompleteLogic mMarkFeedbackItemAsCompleteLogic;
    private final Database mDatabase;

    @PostMapping("/complete-feedback-item/{itemId}")
    public ResponseEntity<?> completeFeedbackItem(
        @PathVariable("itemId") final Long itemId
    ) {
        if (itemId == null) {
            return badRequest();
        }

        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new MarkFeedbackItemAsCompleteCommand(userId, itemId, mMarkFeedbackItemAsCompleteLogic));
    }

    @PostMapping("/close-feedback-item/{itemId}")
    public ResponseEntity<?> closeFeedbackItem(
        @PathVariable("itemId") final Long itemId
    ) {
        if (itemId == null) {
            return badRequest();
        }

        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new MarkFeedbackItemAsWillNotDoCommand(userId, itemId, mDatabase));
    }
}