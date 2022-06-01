package f1_Info.configuration.web.users;

import common.constants.email.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class F1UserDetails implements UserDetails {
    private final Long mUserId;
    @Getter
    private final Email mEmail;
    @ToString.Exclude
    private final String mPassword;
    private final Authority mAuthority;
    private final boolean mEnabled;

    public static F1UserDetails createNewUser(final Email email, final String password) {
        return new F1UserDetails(null, email, password, Authority.USER, false);
    }

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

    public Optional<Long> getUserId() {
        return Optional.ofNullable(mUserId);
    }
}
