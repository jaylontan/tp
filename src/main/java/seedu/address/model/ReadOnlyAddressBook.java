package seedu.address.model;

import java.util.Collection;
import java.util.HashMap;

import javafx.collections.ObservableList;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.UniqueBookingList;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    Collection<Booking> getBookingsSet();

    HashMap<Integer, Booking> getBookings();

    UniqueBookingList getBookingList();
}
