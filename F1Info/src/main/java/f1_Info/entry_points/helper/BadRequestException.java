package f1_Info.entry_points.helper;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadRequestException extends IllegalArgumentException {
    public BadRequestException(final String s) {
        super(s);
    }
}
