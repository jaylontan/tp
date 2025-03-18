package seedu.address.model.booking.exceptions;

/**
 * Signals that the operation is unable to find the specified booking.
 */
public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException() {
        super("Booking not found in list");
    }
}
