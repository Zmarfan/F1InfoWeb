package f1_Info.entry_points.authentication.commands.forgot_password_command;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ForgotPasswordEmail {
    public static final String HTML_MESSAGE = """
        <div>
            <p>Hello!</p>
        
            <p>Please click the link below to create a new password for your F1Info account. If you did not request for a password change you can ignore this email.</p>
            <a href="%s">Create new password</a>
        
            <p>Thanks,</p>
            <p>F1Info</p>
        
            <p>(This notification has been sent to the email address associated with your newly registered F1Info account. This email message was auto-generated. Please do not respond.)</p>
        </div>
    """;
}