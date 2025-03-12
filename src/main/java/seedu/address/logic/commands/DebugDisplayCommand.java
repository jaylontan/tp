package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Command to display all current bookings in the AddressBook.
 */
public class DebugDisplayCommand extends Command {
    public static final String COMMAND_WORD = "display";

    @Override
    public CommandResult execute(Model model) {
        // Retrieve the booking list directly from AddressBook
        String bookingsList = model.getAddressBook().getBookingListAsString();

        // Return the booking list result
        return new CommandResult("Bookings List:\n" + bookingsList);
    }
}
