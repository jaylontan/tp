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

    @FXML
    private Label status;

    /**
     * Creates a {@code BookingCode} with the given {@code Booking} to display.
     */
    public BookingCard(Booking booking) {
        super(FXML);
        this.booking = booking;

        bookingId.setText("ID: " + booking.getBookingId());

        if (booking.getBookingPerson() != null) {
            bookingPerson.setText(booking.getBookingPerson().getName().fullName);
            phoneNumber.setText(booking.getBookingPerson().getPhone().value);
        } else {
            bookingPerson.setText("No Person");
            phoneNumber.setText("No Phone");
        }

        bookingDate.setText(formatDateTime(booking.getBookingDateTime()));
        pax.setText(booking.getPax() + " pax");
        remarks.setText(booking.getRemarks());
        status.textProperty().bind(booking.getStatusProperty());
        booking.getStatusProperty().addListener((observable, oldValue, newValue) -> updateStatusColor(newValue.toString()));
        updateStatusColor(booking.getStatus().toString());
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");
        return dateTime.format(formatter);
    }

    private void updateStatusColor(String text) {
        String baseStyle =
                "-fx-font-size: 11px;" +
                        "-fx-padding: 2 8 2 8;" +
                        "-fx-background-radius: 12;" +
                        "-fx-font-weight: bold;";

        if (text == null) {
            status.setStyle(baseStyle + " -fx-text-fill: #E0E0E0;");
            return;
        }

        switch (text) {
        case "Upcoming":
            status.setStyle(baseStyle + " -fx-text-fill: #FFD93D;");
            break;
        case "Cancelled":
            status.setStyle(baseStyle + " -fx-text-fill: #FF0000;");
            break;
        case "Completed":
            status.setStyle(baseStyle + " -fx-text-fill: #00FF00;");
            break;
        default:
            status.setStyle(baseStyle + " -fx-text-fill: #E0E0E0;");
        }
    }
}
