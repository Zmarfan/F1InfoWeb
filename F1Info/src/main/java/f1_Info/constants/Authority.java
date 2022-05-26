package f1_Info.constants;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class Authority implements GrantedAuthority {
    public static final Authority USER = new Authority("user");
    public static final Authority ADMIN = new Authority("admin");

    private final String mStringCode;

    @Override
    public String getAuthority() {
        return mStringCode;
    }

    public static Authority fromStringCode(final String stringCode) {
        if (USER.getAuthority().equals(stringCode)) {
            return USER;
        }
        if (ADMIN.getAuthority().equals(stringCode)) {
            return ADMIN;
        }
        throw new IllegalArgumentException(String.format("Unable to parse the string code: %s to a valid Authority", stringCode));
    }
}
