package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOKING_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;

/**
 * Marks a booking as UPCOMING, COMPLETED or CANCELLED.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a booking with the given ID as a new status.\n"
            + "Parameters: "
            + PREFIX_BOOKING_ID + "BOOKING_ID "
            + PREFIX_STATUS + "STATUS (UPCOMING, COMPLETED, CANCELLED)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_BOOKING_ID + "2 "
            + PREFIX_STATUS + "COMPLETED";

    public static final String MESSAGE_SUCCESS = "Booking %d marked as %s.";
    public static final String MESSAGE_INVALID_ID = "Booking ID %d does not exist.";
    public static final String MESSAGE_INVALID_STATUS =
            "Invalid status! Use UPCOMING, COMPLETED or CANCELLED.";

    private final int bookingId;
    private final Booking.Status newStatus;

    /**
     * Creates a Mark Command to change the status of the specified {@code Booking}
     */
    public MarkCommand(int bookingId, Booking.Status newStatus) {
        this.bookingId = bookingId;
        this.newStatus = newStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        AddressBook addressBook = (AddressBook) model.getAddressBook();

        if (!addressBook.hasBooking(bookingId)) {
            throw new CommandException(String.format(MESSAGE_INVALID_ID, bookingId));
        }

        addressBook.setBookingStatus(bookingId, newStatus);

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
