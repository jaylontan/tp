package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.booking.Booking;

/**
 * An UI component that displays information of a {@code Booking}.
 */
public class BookingCard extends UiPart<Region> {
    private static final String FXML = "BookingCard.fxml";
    public final Booking booking;

    @FXML
    private Label bookingId;

    @FXML
    private Label bookingPerson;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label bookingDate;

    @FXML
    private Label pax;

    @FXML
    private Label remarks;

    /**
     * Creates a {@code BookingCode} with the given {@code Booking} to display.
     */
    public BookingCard(Booking booking) {
        super(FXML);
        this.booking = booking;

        bookingId.setText(booking.getBookingId() + ".");

        if (booking.getBookingPerson() != null) {
            bookingPerson.setText("Name: " + booking.getBookingPerson().getName().fullName);
            phoneNumber.setText("Phone: " + booking.getBookingPerson().getPhone().value);
        } else {
            bookingPerson.setText("No Person");
            phoneNumber.setText("No Phone");
        }

        bookingDate.setText(formatDateTime(booking.getBookingDateTime()));
        pax.setText(booking.getPax() + " pax");
        remarks.setText(booking.getRemarks());
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");
        return dateTime.format(formatter);
    }
}
