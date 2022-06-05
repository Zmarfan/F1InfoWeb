package f1_Info.entry_points.authentication.services;

import common.constants.email.Email;
import common.constants.email.MalformedEmailException;
import f1_Info.configuration.web.users.Authority;
import f1_Info.configuration.web.users.F1UserDetails;
import f1_Info.configuration.web.users.SecurityContextWrapper;
import f1_Info.configuration.web.users.SessionAttributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    private static final long USER_ID = 23L;
    private static final String PASSWORD = "password";

    @Mock
    SecurityContextWrapper mSecurityContextWrapper;

    @Mock
    AuthenticationManager mAuthenticationManager;

    @Mock
    Authentication mAuthentication;

    @Mock
    HttpSession mSession;

    @Mock
    HttpServletRequest mRequest;

    @InjectMocks
    AuthenticationService mAuthenticationService;

    private Email mEmail;

    @BeforeEach
    public void init() throws MalformedEmailException {
        mEmail = new Email("test@test.com");
    }

    @Test
    void should_not_set_authentication_if_unable_to_authenticate_user_details_when_logging_in() {
        when(mAuthenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new DisabledException(""));

        assertThrows(AuthenticationException.class, () -> mAuthenticationService.login(mEmail, PASSWORD, mRequest));
        verify(mSecurityContextWrapper, never()).setAuthentication(any(Authentication.class));
    }

    @Test
    void should_not_create_session_if_unable_to_authenticate_user_details_when_logging_in() {
        when(mAuthenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new DisabledException(""));

        assertThrows(AuthenticationException.class, () -> mAuthenticationService.login(mEmail, PASSWORD, mRequest));
        verify(mRequest, never()).getSession(true);
    }

    @Test
    void should_set_authentication_when_auto_logging_in() {
        when(mRequest.getSession(anyBoolean())).thenReturn(mSession);

        mAuthenticationService.autoLogin(USER_ID, mEmail, mRequest);

        verify(mSecurityContextWrapper).setAuthentication(new UsernamePasswordAuthenticationToken(mEmail.read(), null, List.of(Authority.USER)));
    }

    @Test
    void should_create_session_when_auto_logging_in() {
        when(mRequest.getSession(anyBoolean())).thenReturn(mSession);

        mAuthenticationService.autoLogin(USER_ID, mEmail, mRequest);

        verify(mRequest).getSession(true);
    }

    @Test
    void should_set_user_id_attribute_when_auto_logging_in() {
        when(mRequest.getSession(anyBoolean())).thenReturn(mSession);

        mAuthenticationService.autoLogin(USER_ID, mEmail, mRequest);

        verify(mSession).setAttribute(SessionAttributes.USER_ID, USER_ID);
    }

    @Nested
    class SuccessfulAuthenticationSection {
        @BeforeEach
        public void init() {
            when(mAuthenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mAuthentication);
            when(mAuthentication.getPrincipal()).thenReturn(createF1UserDetails());
            when(mRequest.getSession(anyBoolean())).thenReturn(mSession);
        }

        @Test
        void should_set_authentication_when_logging_in() {
            mAuthenticationService.login(mEmail, PASSWORD, mRequest);

            verify(mSecurityContextWrapper).setAuthentication(mAuthentication);
        }

        @Test
        void should_create_session_when_logging_in() {
            mAuthenticationService.login(mEmail, PASSWORD, mRequest);

            verify(mRequest).getSession(true);
        }

        @Test
        void should_set_user_id_attribute_when_logging_in() {
            mAuthenticationService.login(mEmail, PASSWORD, mRequest);

            verify(mSession).setAttribute(SessionAttributes.USER_ID, USER_ID);
        }
    }



    private F1UserDetails createF1UserDetails() {
        return new F1UserDetails(USER_ID, mEmail, PASSWORD, Authority.USER, true);
    }
}
