package f1_Info.entry_points;

import common.constants.email.Email;
import common.constants.email.MalformedEmailException;
import common.logger.Logger;
import f1_Info.configuration.web.users.Authority;
import f1_Info.configuration.web.users.SecurityContextWrapper;
import f1_Info.configuration.web.users.SessionAttributes;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.Command;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.helper.ForbiddenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static f1_Info.configuration.web.ResponseUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EndpointHelperTest {
    private static final long USER_ID = 1;

    @Mock
    Command mTestCommand;

    @Mock
    HttpServletRequest mRequest;

    @Mock
    HttpSession mHttpSession;

    @Mock
    Logger mLogger;

    @Mock
    Authentication mAuthentication;

    @Mock
    SecurityContextWrapper mSecurityContextWrapper;

    @InjectMocks
    EndpointHelper mEndpointHelper;

    @Test
    void should_call_command_execute() throws Exception {
        when(mRequest.getSession(false)).thenReturn(mHttpSession);
        when(mHttpSession.getAttribute(SessionAttributes.USER_ID)).thenReturn(USER_ID);

        mEndpointHelper.runCommand(mRequest, (userId) -> mTestCommand);

        verify(mTestCommand).execute();
    }

    @Test
    void should_return_ok_when_successfully_executing_command() throws Exception {
        when(mRequest.getSession(false)).thenReturn(mHttpSession);
        when(mHttpSession.getAttribute(SessionAttributes.USER_ID)).thenReturn(USER_ID);
        when(mTestCommand.execute()).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        assertEquals(ok(), mEndpointHelper.runCommand(mRequest, (userId) -> mTestCommand));
    }

    @Test
    void should_return_forbidden_if_creation_of_the_command_produces_forbidden_exception() {
        when(mRequest.getSession(false)).thenReturn(mHttpSession);
        when(mHttpSession.getAttribute(SessionAttributes.USER_ID)).thenReturn(USER_ID);

        assertEquals(forbidden(), mEndpointHelper.runCommand(mRequest, (userId) -> {
            throw new ForbiddenException();
        }));
    }

    @Test
    void should_return_bad_request_if_creation_of_the_command_produces_bad_request_exception() {
        when(mRequest.getSession(false)).thenReturn(mHttpSession);
        when(mHttpSession.getAttribute(SessionAttributes.USER_ID)).thenReturn(USER_ID);

        assertEquals(badRequest(), mEndpointHelper.runCommand(mRequest, (userId) -> {
            throw new BadRequestException();
        }));
    }

    @Test
    void should_return_internal_server_error_if_exception_occurs_while_executing_command() throws Exception {
        when(mRequest.getSession(false)).thenReturn(mHttpSession);
        when(mHttpSession.getAttribute(SessionAttributes.USER_ID)).thenReturn(USER_ID);

        when(mTestCommand.execute()).thenThrow(new Exception());

        assertEquals(internalServerError(), mEndpointHelper.runCommand(mRequest, (userId) -> mTestCommand));
    }

    @Test
    void should_log_severe_if_exception_occurs_while_executing_command() throws Exception {
        when(mRequest.getSession(false)).thenReturn(mHttpSession);
        when(mHttpSession.getAttribute(SessionAttributes.USER_ID)).thenReturn(USER_ID);

        when(mTestCommand.execute()).thenThrow(new Exception());

        mEndpointHelper.runCommand(mRequest, (userId) -> mTestCommand);

        verify(mLogger).severe(anyString(), eq(mTestCommand.getClass()), anyString(), any(Exception.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void should_throw_bad_request_exception_if_email_string_is_null_or_empty(final String email) {
        assertThrows(BadRequestException.class, () -> mEndpointHelper.convertEmail(email));
    }

    @Test
    void should_throw_bad_request_exception_if_email_string_can_not_be_parsed_to_email() {
        assertThrows(BadRequestException.class, () -> mEndpointHelper.convertEmail("test@test."));
    }

    @Test
    void should_return_email_if_possible_to_parse_from_string() throws MalformedEmailException {
        final String validEmail = "valid@email.com";
        assertEquals(new Email(validEmail), mEndpointHelper.convertEmail(validEmail));
    }

    @Test
    void should_return_true_when_checking_if_user_is_logged_in_if_current_user_is_authenticated_and_has_a_none_anonymous_role() {
        when(mSecurityContextWrapper.getAuthentication()).thenReturn(mAuthentication);
        when(mAuthentication.isAuthenticated()).thenReturn(true);
        when(mAuthentication.getAuthorities()).thenReturn((Collection)List.of(Authority.USER));

        assertTrue(mEndpointHelper.isLoggedIn());
    }

    @Test
    void should_return_false_when_checking_if_user_is_logged_in_if_current_user_is_not_authenticated() {
        when(mSecurityContextWrapper.getAuthentication()).thenReturn(mAuthentication);
        when(mAuthentication.isAuthenticated()).thenReturn(false);
        when(mAuthentication.getAuthorities()).thenReturn(new ArrayList<>());

        assertFalse(mEndpointHelper.isLoggedIn());
    }
}
