package f1_Info.entry_points.helper;

public class BadRequestException extends IllegalArgumentException {
    public BadRequestException(final String s) {
        super(s);
    }
}
