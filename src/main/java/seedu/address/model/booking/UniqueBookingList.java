package seedu.address.model.booking;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.booking.exceptions.BookingNotFoundException;
import seedu.address.model.booking.exceptions.DuplicateBookingException;

/**
 * A list of bookings that enforces uniqueness between its elements and does not allow nulls.
 * A booking is considered unique by its booking ID.
 *
 * This class wraps a {@code HashMap<Integer, Booking>} to provide O(1) lookups by booking ID.
 */
public class UniqueBookingList implements Iterable<Booking> {

    private final Map<Integer, Booking> internalMap = new HashMap<>();
    private final ObservableList<Booking> internalList = FXCollections.observableArrayList();
    private final ObservableList<Booking> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains a booking with the same ID as the given booking.
     */
    public boolean contains(int bookingId) {
        requireNonNull(bookingId);
        return internalMap.containsKey(bookingId);
    }

    /**
     * Adds a booking to the list.
     * The booking must not already exist in the list.
     *
     * @throws DuplicateBookingException if a booking with the same ID already exists.
     */
    public void add(Booking booking) {
        requireNonNull(booking);
        if (contains(booking.getBookingId())) {
            throw new DuplicateBookingException();
        }
        internalMap.put(booking.getBookingId(), booking);
        internalList.add(booking);
    }

    /**
     * Removes the booking with the specified booking ID from the list.
     *
     * @param bookingId the booking ID of the booking to remove.
     * @throws BookingNotFoundException if no booking with the given ID exists.
     */
    public void removeById(int bookingId) {
        Booking removed = internalMap.remove(bookingId);
        if (removed == null) {
            throw new BookingNotFoundException();
        }
        internalList.remove(removed);
    }

    /**
     * Removes the requested booking from the list.
     *
     * @param booking the booking to remove.
     * @throws BookingNotFoundException if no booking with the given ID exists.
     */
    public void remove(Booking booking) {
        Booking removed = internalMap.remove(booking.getBookingId());
        if (removed == null) {
            throw new BookingNotFoundException();
        }
        internalList.remove(removed);
    }

    /**
     * Replaces the booking {@code target} in the list with {@code editedBooking}.
     * {@code target} must exist in the list.
     * The booking identity of {@code editedBooking} must not be the same as another existing booking in the list.
     *
     * @throws BookingNotFoundException if the target booking is not found.
     * @throws DuplicateBookingException if the edited booking's ID is different from the target and already exists.
     */
    public void setBooking(Booking target, Booking editedBooking) {
        requireAllNonNull(target, editedBooking);

        int targetId = target.getBookingId();
        int editedId = editedBooking.getBookingId();

        if (!contains(targetId)) {
            throw new BookingNotFoundException();
        }
        if (targetId != editedId && contains(editedId)) {
            throw new DuplicateBookingException();
        }

        internalMap.remove(targetId);
        internalMap.put(editedId, editedBooking);

        int index = internalList.indexOf(target);
        internalList.set(index, editedBooking);
    }

    /**
     * Replaces the contents of this list with {@code bookings}.
     * The given collection must not contain duplicate bookings.
     *
     * @throws DuplicateBookingException if duplicates are found.
     */
    public void setBookings(List<Booking> bookings) {
        requireNonNull(bookings);
        Map<Integer, Booking> tempMap = new HashMap<>();
        for (Booking booking : bookings) {
            if (tempMap.containsKey(booking.getBookingId())) {
                throw new DuplicateBookingException();
            }
            tempMap.put(booking.getBookingId(), booking);
        }

        internalMap.clear();
        internalList.clear();
        internalMap.putAll(tempMap);
        internalList.addAll(bookings);
    }

    /**
     * Returns the booking with the given booking ID.
     *
     * @param bookingId The booking ID of the booking to retrieve.
     * @return the booking corresponding to the given booking ID, or null if not found.
     */
    public Booking getBooking(int bookingId) {
        return internalMap.get(bookingId);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Booking> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns all upcoming bookings.
     */
    public List<Booking> getUpcomingBookings() {
        return internalList.stream()
                .filter(booking -> booking.getStatus() == Status.UPCOMING)
                .sorted((b1, b2) -> b1.getBookingDateTime().compareTo(b2.getBookingDateTime()))
                .toList();
    }


    /**
     * Returns all cancelled or completed bookings.
     */
    public List<Booking> getCancelledOrCompletedBookings() {
        return internalList.stream()
                .filter(booking -> booking.getStatus() != Status.UPCOMING)
                .sorted((b1, b2) -> b1.getBookingDateTime().compareTo(b2.getBookingDateTime()))
                .toList();
    }

    /**
     * Sets the status of the booking with the given booking ID.
     */
    public void setBookingStatus(int bookingId, Status newStatus) {
        Booking booking = getBooking(bookingId);
        if (booking == null) {
            throw new BookingNotFoundException();
        }
        booking.setStatus(newStatus);
    }

    /**
     * Returns a string representation of all bookings.
     */
    public String getAllBookingsAsString() {
        StringBuilder sb = new StringBuilder();
        for (Booking booking : internalMap.values()) {
            sb.append(booking.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a string representation of upcoming bookings.
     */
    public String getUpcomingBookingsAsString() {
        StringBuilder sb = new StringBuilder();
        for (Booking booking : getUpcomingBookings()) {
            sb.append(booking.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Removes all cancelled or completed bookings.
     */
    public void clearBookings(List<Booking> bookingsToClear) {
        for (Booking booking : bookingsToClear) {
            int id = booking.getBookingId();
            internalMap.remove(id);
        }

        internalList.removeAll(bookingsToClear);
    }

    @Override
    public Iterator<Booking> iterator() {
        return internalMap.values().iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UniqueBookingList)) {
            return false;
        }
        UniqueBookingList otherList = (UniqueBookingList) other;
        return internalMap.equals(otherList.internalMap);
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }

    @Override
    public String toString() {
        return internalMap.values().toString();
    }
}
