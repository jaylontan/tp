package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
            + ": Filters the bookings of the person identified "
            + "by their phone number.\n"
            + "Parameters: p/PHONE_NUMBER\n"
            + "Example: " + COMMAND_WORD + " p/98765432";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET =
            "Filter command not implemented yet";

    public static final String MESSAGE_PERSON_NOT_FOUND = "No person found with phone number: %s";
    public static final String MESSAGE_NO_BOOKINGS = "Person with phone number %s has no bookings.";
    public static final String MESSAGE_SUCCESS = "Here are the bookings for %s:";

    private final Phone phoneNumber;

    /**
     * Creates a Filter Command to list the bookings of specified {@code Person}
     */
    public FilterCommand(Phone phoneNumber) {
        requireAllNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AddressBook addressBook = (AddressBook) model.getAddressBook();

        // Find person by phone number
        Person person = addressBook.getPersonList().stream()
                .filter(p -> p.getPhone().equals(phoneNumber))
                .findFirst()
                .orElse(null);

        if (person == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, phoneNumber));
        }

        Predicate<Booking> predicate = booking -> person.getBookingIDs().contains(booking.getBookingId());
        model.updateFilteredBookingList(predicate);

        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_BOOKINGS, phoneNumber));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, person.getName()));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterCommand
                && phoneNumber.equals(((FilterCommand) other).phoneNumber));
    }
}
