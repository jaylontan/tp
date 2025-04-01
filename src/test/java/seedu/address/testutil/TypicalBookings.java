package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.booking.Booking;

/**
 * A utility class containing a list of {@code Booking} objects to be used in tests.
 */
public class TypicalBookings {

    public static final Booking ALICES_BOOKING = new BookingBuilder()
            .withBookingPerson(TypicalPersons.ALICE)
            .withBookingDateTime("2025-10-10T10:00")
            .withBookingMadeDateTime("2021-10-10T10:00")
            .withRemarks("No remarks")
            .withPax("1")
            .withStatus("UPCOMING")
            .build();
    public static final Booking BENSONS_BOOKING = new BookingBuilder()
            .withBookingPerson(TypicalPersons.BENSON)
            .withBookingDateTime("2025-03-31T20:00")
            .withBookingMadeDateTime("2021-10-10T09:00")
            .withRemarks("Dinner")
            .withPax("6")
            .withStatus("COMPLETED")
            .build();
    public static final Booking CARLS_BOOKING = new BookingBuilder()
            .withBookingPerson(TypicalPersons.CARL)
            .withBookingDateTime("2025-04-15T19:00")
            .withBookingMadeDateTime("2023-10-10T09:00")
            .withRemarks("Birthday Dinner")
            .withPax("4")
            .withStatus("UPCOMING")
            .build();
    public static final Booking DANIELS_BOOKING = new BookingBuilder()
            .withBookingPerson(TypicalPersons.DANIEL)
            .withBookingDateTime("2025-06-25T18:00")
            .withBookingMadeDateTime("2024-10-20T10:00")
            .withRemarks("Date Night")
            .withPax("2")
            .withStatus("UPCOMING")
            .build();
    public static final Booking ELLES_BOOKING = new BookingBuilder()
            .withBookingPerson(TypicalPersons.ELLE)
            .withBookingDateTime("2025-05-30T11:00")
            .withBookingMadeDateTime("2024-01-10T12:00")
            .withRemarks("Brunch")
            .withPax("3")
            .withStatus("CANCELLED")
            .build();
    public static final Booking FIONAS_BOOKING = new BookingBuilder()
            .withBookingPerson(TypicalPersons.FIONA)
            .withBookingDateTime("2025-10-31T20:00")
            .withBookingMadeDateTime("2025-03-10T19:00")
            .withRemarks("Family Dinner")
            .withPax("6")
            .withStatus("CANCELLED")
            .build();
    public static final Booking GEORGES_BOOKING = new BookingBuilder()
            .withBookingPerson(TypicalPersons.GEORGE)
            .withBookingDateTime("2025-05-10T09:00")
            .withBookingMadeDateTime("2025-01-10T10:00")
            .withRemarks("Breakfast")
            .withPax("3")
            .withStatus("UPCOMING")
            .build();

    private TypicalBookings() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and bookings.
     */
    public static AddressBook getTypicalAddressBookWithBookings() {
        AddressBook ab = TypicalPersons.getTypicalAddressBook();
        for (Booking booking : getTypicalBookings()) {
            ab.addBooking(booking);
        }
        return ab;
    }

    public static List<Booking> getTypicalBookings() {
        return new ArrayList<>(Arrays.asList(ALICES_BOOKING, BENSONS_BOOKING, CARLS_BOOKING, DANIELS_BOOKING,
                ELLES_BOOKING, FIONAS_BOOKING, GEORGES_BOOKING));
    }
}
