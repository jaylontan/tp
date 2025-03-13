package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOKING_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Locale;
import java.util.stream.Stream;

import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.Status;

/**
 * Parses input arguments and creates a new MarkCommand object.
 */
public class MarkCommandParser implements Parser<MarkCommand> {

    @Override
    public MarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BOOKING_ID, PREFIX_STATUS);

        if (!arePrefixesPresent(argMultimap, PREFIX_BOOKING_ID, PREFIX_STATUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        int bookingId;
        try {
            bookingId = Integer.parseInt(argMultimap.getValue(PREFIX_BOOKING_ID).orElseThrow());
        } catch (NumberFormatException e) {
            throw new ParseException("Booking ID must be a valid integer.");
        }

        Status newStatus;
        try {
            newStatus = Status.fromString(argMultimap.getValue(PREFIX_STATUS)
                    .orElseThrow()
                    .toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new ParseException(MarkCommand.MESSAGE_INVALID_STATUS);
        }

        return new MarkCommand(bookingId, newStatus);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
