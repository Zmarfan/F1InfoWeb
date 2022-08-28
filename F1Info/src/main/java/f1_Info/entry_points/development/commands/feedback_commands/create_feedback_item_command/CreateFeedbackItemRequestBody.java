package f1_Info.entry_points.development.commands.feedback_commands.create_feedback_item_command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class CreateFeedbackItemRequestBody {
    String mText;

    @JsonCreator
    public CreateFeedbackItemRequestBody(
        @JsonProperty("text") String text
    ) {
        mText = text;
    }
}