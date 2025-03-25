package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOKING_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;

import seedu.address.logic.commands.EditBookingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditBookingCommand object.
 */
public class EditBookingCommandParser implements Parser<EditBookingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBookingCommand
     * and returns an EditBookingCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public EditBookingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BOOKING_ID, PREFIX_DATE, PREFIX_PAX, PREFIX_REMARK, PREFIX_TAG);

        if (argMultimap.getValue(PREFIX_BOOKING_ID).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBookingCommand.MESSAGE_USAGE));
        }

        int bookingId;
        try {
            bookingId = Integer.parseInt(argMultimap.getValue(PREFIX_BOOKING_ID).orElseThrow());
        } catch (NumberFormatException e) {
            throw new ParseException("Booking ID must be a valid integer.");
        }

        // Create a HashMap to store the fields to edit
        HashMap<String, Object> fieldsToEdit = new HashMap<>();

        // Parse and add bookingDateTime if present
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            LocalDateTime bookingDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE).get());
            fieldsToEdit.put("bookingDateTime", bookingDateTime);
        }

        // Parse and add pax if present
        if (argMultimap.getValue(PREFIX_PAX).isPresent()) {
            int pax = ParserUtil.parsePax(argMultimap.getValue(PREFIX_PAX).get());
            fieldsToEdit.put("pax", pax);
        }

        // Parse and add remarks if present
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            String remarks = argMultimap.getValue(PREFIX_REMARK).get();
            fieldsToEdit.put("remarks", remarks);
        }

        // Parse and add tags if present
        if (!argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            fieldsToEdit.put("tags", tags);
        }

        // Ensure at least one field is edited
        if (fieldsToEdit.isEmpty()) {
            throw new ParseException(EditBookingCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBookingCommand(bookingId, fieldsToEdit);
    }
}
