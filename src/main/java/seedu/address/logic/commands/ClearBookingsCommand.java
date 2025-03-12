package seedu.address.logic.commands;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears all cancelled and completed bookings.
 */
public class ClearBookingsCommand extends Command {

    public static final String COMMAND_WORD = "clearbookings";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all cancelled and completed bookings\n";

    public static final String MESSAGE_SUCCESS = "Cancelled and completed bookings have been cleared!";

    public static final String MESSAGE_NO_BOOKINGS_TO_CLEAR = "No cancelled or completed bookings to clear!";

    @Override
    public CommandResult execute(Model model) {

        AddressBook addressBook = (AddressBook) model.getAddressBook();

        if (!addressBook.hasCancelledOrCompletedBookings()) {
            return new CommandResult(MESSAGE_NO_BOOKINGS_TO_CLEAR);
        }

        addressBook.clearBookings();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}

