package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOKING_ID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;

import java.util.List;

/**
 * Marks a booking as UPCOMING, COMPLETED, ONGOING or CANCELLED.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a booking with the given ID as a new status.\n"
            + "Parameters: "
            + PREFIX_BOOKING_ID + "BOOKING_ID "
            + PREFIX_STATUS + "STATUS (UPCOMING, COMPLETED, ONGOING, CANCELLED)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_BOOKING_ID + "2 "
            + PREFIX_STATUS + "COMPLETED";

    public static final String MESSAGE_SUCCESS = "Booking %d marked as %s.";
    public static final String MESSAGE_INVALID_ID = "Booking ID %d does not exist.";
    public static final String MESSAGE_INVALID_STATUS = "Invalid status! Use UPCOMING, COMPLETED, ONGOING, or CANCELLED.";

    private final int bookingId;
    private final Booking.Status newStatus;

    public MarkCommand(int bookingId, Booking.Status newStatus) {
        this.bookingId = bookingId;
        this.newStatus = newStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Booking> bookings = model.getAddressBook().getBookings();
        if (bookingId < 0 || bookingId >= bookings.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_ID, bookingId));
        }

        Booking booking = bookings.get(bookingId);
        booking.setStatus(newStatus);

        return new CommandResult(String.format(MESSAGE_SUCCESS, bookingId, newStatus));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherCommand = (MarkCommand) other;
        return bookingId == otherCommand.bookingId && newStatus == otherCommand.newStatus;
    }
}
