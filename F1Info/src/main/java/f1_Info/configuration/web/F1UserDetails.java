package f1_Info.configuration.web;

import f1_Info.constants.Authority;
import f1_Info.constants.Email;
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
    private final String mHashedPassword;
    private final List<Authority> mAuthorities;
    private final boolean mEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mAuthorities;
    }

    @Override
    public String getUsername() {
        return mEmail.getEmail();
    }

    @Override
    public String getPassword() {
        return mHashedPassword;
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
