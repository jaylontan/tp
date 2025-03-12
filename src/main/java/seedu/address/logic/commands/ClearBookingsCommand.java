package seedu.address.logic.commands;

import seedu.address.model.Model;

public class ClearBookingsCommand extends Command {
    public static final String COMMAND_WORD = "clearbookings";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from clear bookings");
    }
}

