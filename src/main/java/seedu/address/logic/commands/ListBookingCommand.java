package seedu.address.logic.commands;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Command to display all (upcoming) bookings in the AddressBook.
 */
public class ListBookingCommand extends Command {
    public static final String COMMAND_WORD = "blist";

    public static final String MESSAGE_SUCCESS = "Here are the upcoming bookings:";
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
        AddressBook addressBook = (AddressBook) model.getAddressBook();
        String bookingsAsList;

        if (isDisplayAll) {
            if (!addressBook.hasAnyBookings()) {
                return new CommandResult(MESSAGE_NO_BOOKINGS);
            } else {
                bookingsAsList = addressBook.getBookingList().getAllBookingsAsString();
            }
        } else {
            if (!addressBook.hasUpcomingBookings()) {
                return new CommandResult(MESSAGE_NO_PENDING_BOOKINGS);
            } else {
                bookingsAsList = addressBook.getBookingList().getUpcomingBookingsAsString();
            }
        }


        String resultMessage = isDisplayAll ? MESSAGE_SUCCESS_ALL : MESSAGE_SUCCESS;
        return new CommandResult(resultMessage + "\n" + bookingsAsList);

    }
}

