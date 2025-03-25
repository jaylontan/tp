package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Changes the remark of an existing person in the address book.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the bookings by phone number, date, or both.\n"
            + "Parameters: [p/PHONE_NUMBER] [d/DATE]\n"
            + "At least one parameter must be provided.\n"
            + "Example 1: " + COMMAND_WORD + " p/98765432\n"
            + "Example 2: " + COMMAND_WORD + " d/2023-12-25\n"
            + "Example 3: " + COMMAND_WORD + " p/98765432 d/2023-12-25";

    public static final String MESSAGE_PERSON_NOT_FOUND = "No person found with phone number: %s";
    public static final String MESSAGE_NO_BOOKINGS = "No bookings found for %s.";
    public static final String MESSAGE_SUCCESS = "Here are the bookings for %s:";

    private final Phone phoneNumber;
    private final LocalDateTime bookingDate;

    /**
     * Creates a Filter Command to list the bookings of specified {@code Person}
     */
    public FilterCommand(Phone phoneNumber, LocalDateTime bookingDate) {
        this.phoneNumber = phoneNumber;
        this.bookingDate = bookingDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AddressBook addressBook = (AddressBook) model.getAddressBook();

        Predicate<Booking> predicate = booking -> true;
        String filterDescription = "all bookings";

        if (phoneNumber != null) {
            // Find person by phone number
            Person person = addressBook.getPersonByPhone(phoneNumber);

            if (person == null) {
                throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, phoneNumber));
            }

            predicate = predicate.and(booking -> person.getBookingIDs().contains(booking.getBookingId()));
            filterDescription = "phone number " + phoneNumber;
        }

        if (bookingDate != null) {
            // Compare only the date part (year, month, day) ignoring time
            predicate = predicate.and(booking ->
                    booking.getBookingDateTime().toLocalDate().equals(bookingDate.toLocalDate()));

            String formattedDate = bookingDate.toLocalDate().format(
                    DateTimeFormatter.ofPattern("dd MMM yyyy"));

            filterDescription = phoneNumber != null
                    ? filterDescription + " on " + formattedDate
                    : formattedDate;
        }

        model.updateFilteredBookingList(predicate);

        if (model.getFilteredBookingList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_BOOKINGS, filterDescription));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, filterDescription));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterCommand
                && phoneNumber.equals(((FilterCommand) other).phoneNumber));
    }
}
