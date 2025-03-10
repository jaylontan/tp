package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddBookingCommand extends Command {

    public static final String COMMAND_WORD = "book";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a booking "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_PAX + "PAX "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2021-10-01 3:00 PM "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_PAX + "5 "
            + PREFIX_REMARK + "Birthday Celebration "
            + PREFIX_TAG + "Discounted";

    public static final String MESSAGE_SUCCESS = "New booking added: \n%1$s";
    public static final String MESSAGE_INVALID_PERSON = "No person with the given phone number exists";

    // Store these instead of a whole Booking object
    // because a booking should only be created after
    // the phone number is verified.
    // Phone number will be verified upon execute, in the context of the model
    private final Phone phoneToAdd;
    private final LocalDateTime bookingDateToAdd;
    private final Set<Tag> tagListToAdd;
    private final String remarkToAdd;
    private final int paxToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Booking}
     */
    public AddBookingCommand(Phone phone, LocalDateTime bookingDate, Set<Tag> tagList, String remark, int pax) {
        requireNonNull(phone);
        requireNonNull(bookingDate);
        requireNonNull(tagList);
        phoneToAdd = phone;
        bookingDateToAdd = bookingDate;
        tagListToAdd = tagList;
        remarkToAdd = remark;
        paxToAdd = pax;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person bookingMaker = null;
        for (Person person : model.getAddressBook().getPersonList()) {
            if (person.getPhone().equals(phoneToAdd)) {
                bookingMaker = person;
            }
        }
        if (bookingMaker == null) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }

        Booking toAdd = new Booking(bookingMaker, bookingDateToAdd, tagListToAdd, remarkToAdd, paxToAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddBookingCommand)) {
            return false;
        }

        AddBookingCommand otherAddBookingCommand = (AddBookingCommand) other;
        return phoneToAdd.equals(otherAddBookingCommand.phoneToAdd)
                && bookingDateToAdd.equals(otherAddBookingCommand.bookingDateToAdd)
                && tagListToAdd.equals(otherAddBookingCommand.tagListToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("phone", phoneToAdd)
                .add("bookingDate", bookingDateToAdd)
                .add("tagList", tagListToAdd)
                .toString();
    }
}
