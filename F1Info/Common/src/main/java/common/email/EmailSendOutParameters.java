package common.email;

import common.constants.Email;
import lombok.Value;

@Value
public class EmailSendOutParameters {
    Email mRecipient;
    String mSubject;
    String mHtmlMessage;
    EmailType mType;
}
