package seedu.address.model.booking;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents a Booking in the booking list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Booking {
    /**
     * Represents the status of the booking.
     */


    private static int bookingIdCounter = 0;

    // Property field for JavaFX binding
    private final StringProperty statusProperty;

    private int bookingId;
    private Person bookingPerson;
    private LocalDateTime bookingDateTime;
    private LocalDateTime bookingMadeDateTime;
    private Status status;
    private String remarks;
    private int pax;

    /**
     * Default Booking constructor
     */
    public Booking(Person bookingPerson, LocalDateTime bookingDateTime, LocalDateTime bookingMadeDateTime,
                   String remarks, int pax) {
        requireAllNonNull(bookingPerson, bookingDateTime, bookingMadeDateTime);
        this.bookingId = bookingIdCounter;
        this.bookingPerson = bookingPerson;
        this.bookingDateTime = bookingDateTime;
        this.bookingMadeDateTime = bookingMadeDateTime;
        this.status = Status.UPCOMING;
        this.remarks = remarks;
        this.pax = pax;
        this.statusProperty = new SimpleStringProperty(this.status.toString());
    }

    /**
     * Creates a Booking object with time created now.
     */
    public Booking(Person bookingPerson, LocalDateTime bookingDate, String remarks, int pax) {
        this(bookingPerson, bookingDate, LocalDateTime.now(), remarks, pax);
        bookingIdCounter++;
    }

    /**
     * Booking constructor for loading from storage
     */
    public Booking(int bookingId, LocalDateTime bookingDateTime, LocalDateTime bookingMadeDateTime,
                   Status status, String remarks, int pax) {
        requireAllNonNull(bookingDateTime, bookingMadeDateTime, pax);
        this.bookingId = bookingId;
        this.bookingPerson = null;
        this.bookingDateTime = bookingDateTime;
        this.bookingMadeDateTime = bookingMadeDateTime;
        this.status = status;
        this.remarks = remarks;
        this.pax = pax;
        this.statusProperty = new SimpleStringProperty(this.status.toString());
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public LocalDateTime getBookingMadeDateTime() {
        return bookingMadeDateTime;
    }

    public Person getBookingPerson() {
        return bookingPerson;
    }

    public void setBookingPerson(Person newPerson) {
        this.bookingPerson = newPerson;
    }

    public void setStatus(Status status) {
        this.status = status;
        this.statusProperty.set(status.toString());
    }

    public Status getStatus() {
        return status;
    }

    public StringProperty getStatusProperty() {
        return statusProperty;
    }

    public String getRemarks() {
        return remarks;
    }

    public int getPax() {
        return pax;
    }

    public int getBookingId() {
        return bookingId;
    }

    // for when we read from storage
    public static void setBookingIdCounter(int bookingIdCounter) {
        Booking.bookingIdCounter = bookingIdCounter;
    }

    /**
     * Updates the fields of this booking based on the provided map.
     * The keys in the map should be "bookingDateTime", "pax", "remarks", and "tags".
     *
     * @param fieldsToEdit A map containing the fields to edit and their new values.
     */
    public void updateFields(HashMap<String, Object> fieldsToEdit) {
        if (fieldsToEdit.containsKey("bookingDateTime")) {
            this.bookingDateTime = (LocalDateTime) fieldsToEdit.get("bookingDateTime");
        }
        if (fieldsToEdit.containsKey("pax")) {
            this.pax = (int) fieldsToEdit.get("pax");
        }
        if (fieldsToEdit.containsKey("remarks")) {
            this.remarks = (String) fieldsToEdit.get("remarks");
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(bookingId, bookingPerson, bookingDateTime, bookingMadeDateTime, remarks, pax);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("bookingID", bookingId)
                .add("name", bookingPerson.getName())
                .add("phone", bookingPerson.getPhone())
                .add("address", bookingPerson.getAddress())
                .add("bookingDate", bookingDateTime)
                .add("bookedOn", bookingMadeDateTime)
                .add("remarks", remarks)
                .add("status", status.toString())
                .add("pax", pax)
                .toString();
    }

    /**
     * Returns true if both bookings have the same bookingId.
     */
    public boolean isSameBooking(Booking otherBooking) {
        if (otherBooking == this) {
            return true;
        }
        return otherBooking != null
                && otherBooking.getBookingId() == this.getBookingId();
    }

}
