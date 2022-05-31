package f1_Info.entry_points.helper;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ForbiddenException extends IllegalArgumentException {
    public ForbiddenException(final String s) {
        super(s);
    }
}
