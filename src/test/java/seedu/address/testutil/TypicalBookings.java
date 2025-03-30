package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.booking.Booking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class containing a list of {@code Booking} objects to be used in tests.
 */
public class TypicalBookings {

    public static final Booking alicesBooking = new BookingBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Booking BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Booking CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Booking DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Booking ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Booking FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Booking GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    private TypicalBookings() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and bookings.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = TypicalPersons.getTypicalAddressBook();
        for (Booking booking : getTypicalBookings()) {
            ab.addBooking(booking);
        }
        return ab;
    }

    public static List<Booking> getTypicalBookings() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
