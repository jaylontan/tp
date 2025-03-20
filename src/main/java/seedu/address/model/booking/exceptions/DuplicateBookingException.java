package seedu.address.model.booking.exceptions;

/**
 * Signals that the operation will result in duplicate Bookings
 */
public class DuplicateBookingException extends RuntimeException {
    public DuplicateBookingException() {
        super("Operation would result in duplicate bookings");
    }
}
