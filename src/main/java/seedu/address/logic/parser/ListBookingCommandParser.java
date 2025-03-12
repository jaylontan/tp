package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListBookingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListBookingCommand object.
 */
public class ListBookingCommandParser implements Parser<ListBookingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListBookingCommand
     * and returns a ListBookingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListBookingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            // If no arguments are given, default to listing only upcoming bookings
            return new ListBookingCommand(false);
        }

        if (trimmedArgs.equalsIgnoreCase("/all")) {
            // If the argument is "/all", list all bookings
            return new ListBookingCommand(true);
        }

        // If an invalid argument is provided, throw an exception
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListBookingCommand.MESSAGE_USAGE));
    }
}
