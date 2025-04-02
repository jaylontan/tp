package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKINGS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;

/**
 * Deletes a booking identified using its displayed index from the booking list.
 */
public class DeleteBookingCommand extends Command {

    public static final String COMMAND_WORD = "bdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the booking identified by the index number used in the displayed booking list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_BOOKING_SUCCESS = "Deleted Booking: %1$s";

    public static final String MESSAGE_BOOKING_NOT_FOUND = "No booking with ID %1$d was found.";

    private final Index targetIndex;

    public DeleteBookingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AddressBook addressBook = (AddressBook) model.getAddressBook();
        int bookingId = targetIndex.getOneBased();

        List<Booking> lastShownList = model.getFilteredBookingList();
        Booking bookingToDelete = lastShownList.stream()
                .filter(booking -> booking.getBookingId() == bookingId)
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_BOOKING_NOT_FOUND, bookingId)));

        addressBook.removeBooking(bookingToDelete);
        model.updateFilteredBookingList(PREDICATE_SHOW_ALL_BOOKINGS);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DELETE_BOOKING_SUCCESS,
                Messages.format(bookingToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteBookingCommand)) {
            return false;
        }

        DeleteBookingCommand otherDeleteCommand = (DeleteBookingCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }
}
