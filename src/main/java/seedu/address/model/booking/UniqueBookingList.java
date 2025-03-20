package seedu.address.model.booking;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    /**
     * Returns true if the list contains a booking with the same ID as the given booking.
     */
    public boolean contains(Booking booking) {
        requireNonNull(booking);
        return internalMap.containsKey(booking.getBookingId());
    }

    /**
     * Adds a booking to the list.
     * The booking must not already exist in the list.
     *
     * @throws DuplicateBookingException if a booking with the same ID already exists.
     */
    public void add(Booking booking) {
        requireNonNull(booking);
        if (contains(booking)) {
            throw new DuplicateBookingException();
        }
        internalMap.put(booking.getBookingId(), booking);
    }

    /**
     * Removes the booking with the specified booking ID from the list.
     *
     * @param bookingId the booking ID of the booking to remove.
     * @throws BookingNotFoundException if no booking with the given ID exists.
     */
    public void removeById(int bookingId) {
        if (internalMap.remove(bookingId) == null) {
            throw new BookingNotFoundException();
        }
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
        requireNonNull(target);
        requireNonNull(editedBooking);

        int targetId = target.getBookingId();
        if (!internalMap.containsKey(targetId)) {
            throw new BookingNotFoundException();
        }

        // If the edited booking has a different ID and that ID already exists, throw an exception.
        if (editedBooking.getBookingId() != targetId && internalMap.containsKey(editedBooking.getBookingId())) {
            throw new DuplicateBookingException();
        }
        internalMap.put(targetId, editedBooking);
    }

    /**
     * Replaces the contents of this list with {@code bookings}.
     * The given collection must not contain duplicate bookings.
     *
     * @throws DuplicateBookingException if duplicates are found.
     */
    public void setBookings(Collection<Booking> bookings) {
        requireNonNull(bookings);
        Map<Integer, Booking> tempMap = new HashMap<>();
        for (Booking booking : bookings) {
            if (tempMap.containsKey(booking.getBookingId())) {
                throw new DuplicateBookingException();
            }
            tempMap.put(booking.getBookingId(), booking);
        }
        internalMap.clear();
        internalMap.putAll(tempMap);
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
     * Returns an unmodifiable view of the bookings as a collection.
     */
    public Collection<Booking> asUnmodifiableCollection() {
        return Collections.unmodifiableCollection(internalMap.values());
    }

    /**
     * Returns all upcoming bookings.
     */
    public Collection<Booking> getUpcomingBookings() {
        return internalMap.values().stream()
                .filter(booking -> booking.getStatus() == Status.UPCOMING)
                .toList();
    }


    /**
     * Returns all cancelled or completed bookings.
     */
    public Collection<Booking> getCancelledOrCompletedBookings() {
        return internalMap.values().stream()
                .filter(booking -> booking.getStatus() != Status.UPCOMING)
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
