package seedu.address.logic.commands;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

public class ClearBookingsCommand extends Command {
    public static final String COMMAND_WORD = "clearbookings";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all cancelled and completed bookings\n";

    @Override
    public CommandResult execute(Model model) {

        AddressBook addressBook = (AddressBook) model.getAddressBook();

        return new CommandResult("Hello from clear bookings");
    }
}

