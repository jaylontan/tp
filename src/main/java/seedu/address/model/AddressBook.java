package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Status;
import seedu.address.model.booking.UniqueBookingList;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueBookingList bookings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        bookings = new UniqueBookingList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the booking list with {@code bookings}.
     * {@code bookings} map values are used; duplicates are not allowed.
     */
    public void setBookings(List<Booking> bookings) {
        // Convert the HashMap's values to a Collection and delegate to UniqueBookingList.
        this.bookings.setBookings(bookings);

        // Reset booking ID counter to avoid conflicts
        Booking maxIdBooking = bookings.stream()
                .max((b1, b2) -> Integer.compare(b1.getBookingId(), b2.getBookingId()))
                .orElse(null);
        int maxId = maxIdBooking == null ? 0 : maxIdBooking.getBookingId();
        Booking.setBookingIdCounter(maxId + 1); // Increment by 1 to avoid clash
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setBookings(newData.getBookingList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        List<Integer> bookingsToRemove = new ArrayList<>(key.getBookingIDs());
        for (int bookingID : bookingsToRemove) {
            bookings.removeById(bookingID);
        }
    }

    //// booking-level operations
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Removes a booking from the address book by booking ID.
     *
     * @param bookingID The booking ID of the booking to be removed.
     */
    public void removeBooking(int bookingID) {
        bookings.removeById(bookingID);
    }

    /**
     * Removes a booking from the address book by booking object.
     *
     * @param booking The booking to be removed.
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Checks if the address book contains a booking with the given booking ID.
     *
     * @param bookingID The booking ID to be checked.
     * @return true if the address book contains a booking with the given booking ID.
     */
    public boolean hasBooking(int bookingID) {
        return bookings.getBooking(bookingID) != null;
    }

    /**
     * Checks if the address book contains any bookings.
     * @return true if there are bookings in the address book.
     */
    public boolean hasAnyBookings() {
        return !bookings.asUnmodifiableObservableList().isEmpty();
    }

    /***
     * Checks if the booking lists contains any upcoming bookings.
     *
     * @return true if there are upcoming bookings in the address book.
     */
    public boolean hasUpcomingBookings() {
        return !bookings.getUpcomingBookings().isEmpty();
    }

    /***
     * Checks if the booking lists contains any cancelled or completed bookings.
     *
     * @return true if there are cancelled or upcoming bookings in the address book.
     */
    public boolean hasCancelledOrCompletedBookings() {
        return !bookings.getCancelledOrCompletedBookings().isEmpty();
    }

    /**
     * Sets the status of the booking with the given booking ID to the new status.
     *
     * @param bookingID The booking ID of the booking to be updated.
     * @param newStatus The new status of the booking.
     */
    public void setBookingStatus(int bookingID, Status newStatus) {
        bookings.setBookingStatus(bookingID, newStatus);
    }


    /***
     * Removes all cancelled or upcoming bookings from the bookings list
     * and removes their booking IDs from the respective people.
     */
    public void clearBookings() {
        List<Booking> bookingsToClear = new ArrayList<>(bookings.getCancelledOrCompletedBookings());
        for (Booking booking : bookingsToClear) {
            int id = booking.getBookingId();
            Person person = booking.getBookingPerson();
            if (person != null) {
                person.removeBookingID(id);
            }

        }
        bookings.clearBookings(bookingsToClear);
    }


    /**
     * Retrieves a person from the address book by phone number.
     *
     * @param phoneNumber The phone number of the person to be retrieved.
     * @return The person with the given phone number, or null if no such person exists.
     */
    public Person getPersonByPhone(Phone phoneNumber) {
        return this.getPersonList().stream()
                .filter(p -> p.getPhone().equals(phoneNumber))
                .findFirst()
                .orElse(null);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Booking> getBookingList() {
        return bookings.asUnmodifiableObservableList();
    }

    public UniqueBookingList getUniqueBookingList() {
        return bookings;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

}
