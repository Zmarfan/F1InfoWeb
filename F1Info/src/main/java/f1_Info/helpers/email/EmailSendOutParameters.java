package f1_Info.helpers.email;

import f1_Info.constants.Email;
import lombok.Value;

@Value
public class EmailSendOutParameters {
    Email mRecipient;
    String mSubject;
    String mHtmlMessage;
    EmailType mType;
}