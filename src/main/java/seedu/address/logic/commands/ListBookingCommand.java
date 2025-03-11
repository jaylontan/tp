package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.booking.Booking;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Command to display all (upcoming) bookings in the AddressBook.
 */
public class ListBookingCommand extends Command {
    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_SUCCESS = "Here are the upcoming bookings:";
    public static final String MESSAGE_NO_PENDING_BOOKINGS = "There are no upcoming bookings.";
    public static final String MESSAGE_SUCCESS_ALL = "Here are all the bookings:";
    public static final String MESSAGE_NO_BOOKINGS = "There are no bookings.";

    public final boolean isDisplayAll;

    /**
     * Constructor for the ListBookingCommand.
     */
    public ListBookingCommand(boolean isDisplayAll) {
        this.isDisplayAll = isDisplayAll;
    }


    @Override
    public CommandResult execute(Model model) {
        List<Booking> bookings;

        if (isDisplayAll) {
            bookings = model.getAddressBook().getBookings();
            if (bookings.isEmpty()) {
                return new CommandResult(MESSAGE_NO_BOOKINGS);
            }
        } else {
            bookings = model.getAddressBook().getBookings().stream()
                    .filter(booking -> booking.getStatus() == Booking.Status.UPCOMING)
                    .collect(Collectors.toList());
            if (bookings.isEmpty()) {
                return new CommandResult(MESSAGE_NO_PENDING_BOOKINGS);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Booking booking : bookings) {
            sb.append(booking.toString()).append("\n");
        }

        String resultMessage = isDisplayAll ? MESSAGE_SUCCESS_ALL : MESSAGE_SUCCESS;
        return new CommandResult(resultMessage + "\n" + sb.toString());

    }
}

