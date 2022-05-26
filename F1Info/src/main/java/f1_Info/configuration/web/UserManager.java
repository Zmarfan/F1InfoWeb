package f1_Info.configuration.web;

import f1_Info.constants.Authority;
import f1_Info.constants.Email;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManager implements UserDetailsManager {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email.equals("test@cool.com")) {
            return new F1UserDetails(1, new Email("test@cool.com"), new BCryptPasswordEncoder().encode("asdf"), List.of(Authority.USER), true);
        }
        throw new UsernameNotFoundException(String.format("The provided email: %s does not exist", email));
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String email) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String email) {
        return false;
    }
}
