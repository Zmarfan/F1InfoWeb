package f1_Info.entry_points;

import f1_Info.configuration.web.users.SessionAttributes;
import common.logger.Logger;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.Command;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.helper.ForbiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static f1_Info.configuration.web.ResponseUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @InjectMocks
    EndpointHelper mEndpointHelper;

    @BeforeEach
    void init() {
        when(mRequest.getSession(false)).thenReturn(mHttpSession);
        when(mHttpSession.getAttribute(SessionAttributes.USER_ID)).thenReturn(USER_ID);
    }

    @Test
    void should_call_command_execute() throws Exception {
        mEndpointHelper.runCommand(mRequest, (userId) -> mTestCommand);

        verify(mTestCommand).execute();
    }

    @Test
    void should_return_ok_when_successfully_executing_command() throws Exception {
        when(mTestCommand.execute()).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        assertEquals(ok(), mEndpointHelper.runCommand(mRequest, (userId) -> mTestCommand));
    }

    @Test
    void should_return_forbidden_if_creation_of_the_command_produces_forbidden_exception() {
        assertEquals(forbidden(), mEndpointHelper.runCommand(mRequest, (userId) -> {
            throw new ForbiddenException();
        }));
    }

    @Test
    void should_return_bad_request_if_creation_of_the_command_produces_bad_request_exception() {
        assertEquals(badRequest(), mEndpointHelper.runCommand(mRequest, (userId) -> {
            throw new BadRequestException();
        }));
    }

    @Test
    void should_return_internal_server_error_if_exception_occurs_while_executing_command() throws Exception {
        when(mTestCommand.execute()).thenThrow(new Exception());

        assertEquals(internalServerError(), mEndpointHelper.runCommand(mRequest, (userId) -> mTestCommand));
    }

    @Test
    void should_log_severe_if_exception_occurs_while_executing_command() throws Exception {
        when(mTestCommand.execute()).thenThrow(new Exception());

        mEndpointHelper.runCommand(mRequest, (userId) -> mTestCommand);

        verify(mLogger).severe(anyString(), eq(mTestCommand.getClass()), anyString(), any(Exception.class));
    }
}
