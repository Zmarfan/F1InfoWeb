package f1_Info.entry_points.authentication.commands.user_register_command;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserRegistrationEmail {
    public static final String HTML_MESSAGE = """
        <div>
            <p>Hello!</p>
        
            <p>Please click the link below to verify your F1Info email address. Verifying your email address ensures an extra layer of security for your account and allows you to utilize all that F1Info has to offer.</p>
            <a href="%s">Verify Email Address</a>
        
            <p>Thanks,</p>
            <p>F1Info</p>
        
            <p>(This notification has been sent to the email address associated with your newly registered F1Info account. This email message was auto-generated. Please do not respond.)</p>
        </div>
    """;
}
