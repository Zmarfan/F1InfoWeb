package f1_Info.configuration.web.users;

import common.constants.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class F1UserDetails implements UserDetails {
    @Getter
    private final long mUserId;
    private final Email mEmail;
    private final String mPassword;
    private final Authority mAuthority;
    private final boolean mEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(mAuthority);
    }

    @Override
    public String getUsername() {
        return mEmail.read();
    }

    @Override
    public String getPassword() {
        return mPassword;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return mEnabled;
    }
}
