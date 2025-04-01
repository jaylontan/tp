package seedu.address.logic.commands;

import seedu.address.model.Model;


/**
 * Command to display all (upcoming) bookings in the AddressBook.
 */
public class ListBookingCommand extends Command {
    public static final String COMMAND_WORD = "blist";

    public static final String MESSAGE_SUCCESS = "Here are the upcoming bookings:\n include /all to see all bookings";
    public static final String MESSAGE_NO_PENDING_BOOKINGS = "There are no upcoming bookings.";
    public static final String MESSAGE_SUCCESS_ALL = "Here are all the bookings:";
    public static final String MESSAGE_NO_BOOKINGS = "There are no bookings.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists upcoming bookings\n"
            + "[/all]: Lists all bookings\n";

    public final boolean isDisplayAll;

    /**
     * Constructor for the ListBookingCommand.
     */
    public ListBookingCommand(boolean isDisplayAll) {
        this.isDisplayAll = isDisplayAll;
    }


    @Override
    public CommandResult execute(Model model) {

        if (isDisplayAll) {
            model.updateFilteredBookingList(Model.PREDICATE_SHOW_ALL_BOOKINGS);
        } else {
            model.updateFilteredBookingList(Model.PREDICATE_SHOW_UPCOMING_BOOKINGS);
        }

        if (model.getFilteredBookingList().isEmpty()) {
            return new CommandResult(isDisplayAll ? MESSAGE_NO_BOOKINGS : MESSAGE_NO_PENDING_BOOKINGS);
        } else {
            return new CommandResult(isDisplayAll ? MESSAGE_SUCCESS_ALL : MESSAGE_SUCCESS);
        }

    }
}

