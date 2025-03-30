package seedu.address.testutil;

import seedu.address.model.booking.*;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Person objects.
 */
public class BookingBuilder {

    public static final Person DEFAULT_PERSON = TypicalPersons.ALICE;
    public static final String DEFAULT_BOOKING_DATE = "2025-10-10T10:00";
    public static final String DEFAULT_REMARKS = "No remarks";
    public static final int DEFAULT_PAX = 1;

    private int bookingId;
    private Person bookingPerson;
    private LocalDateTime bookingDateTime;
    private LocalDateTime bookingMadeDateTime;
    private Status status;
    private String remarks;
    private int pax;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public BookingBuilder() {
        bookingId = 0;
        bookingPerson = DEFAULT_PERSON;
        bookingDateTime = LocalDateTime.parse(DEFAULT_BOOKING_DATE);
        bookingMadeDateTime = LocalDateTime.now();
        status = Status.UPCOMING;
        remarks = DEFAULT_REMARKS;
        pax = DEFAULT_PAX;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public BookingBuilder(Booking bookingToCopy) {
        bookingId = bookingToCopy.getBookingId();
        bookingPerson = bookingToCopy.getBookingPerson();
        bookingDateTime = bookingToCopy.getBookingDateTime();
        bookingMadeDateTime = bookingToCopy.getBookingMadeDateTime();
        status = bookingToCopy.getStatus();
        remarks = bookingToCopy.getRemarks();
        pax = bookingToCopy.getPax();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public BookingBuilder withPerson(Person person) {
        this.bookingPerson = person;
        return this;
    }

    /**
     * Parses the {@code remarks} of the {@code Booking} that we are building.
     */
    public BookingBuilder withRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public BookingBuilder withStatus(String status) {
        this.status = Status.fromString(status);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public BookingBuilder withPax(String pax) {
        this.pax = Integer.parseInt(pax);
        return this;
    }

    /**
     * Sets the {@code BookingDateTime} of the {@code Booking} that we are building.
     */
    public BookingBuilder withBookingDateTime(String date) {
        this.bookingDateTime = LocalDateTime.parse(date);
        return this;
    }

    /**
     * Sets the {@code BookingMadeDateTime} of the {@code Booking} that we are building.
     */
    public BookingBuilder withBookingMadeDateTime(String date) {
        this.bookingMadeDateTime = LocalDateTime.parse(date);
        return this;
    }

    public Booking build() {
        return new Booking(bookingId, bookingDateTime, bookingMadeDateTime, status, remarks, pax);
    }

}
