package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Person;

import java.util.Arrays;
import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    String getBookingListAsString();

    void addBooking(Booking toAdd);

    List<Booking> getBookings();
}
