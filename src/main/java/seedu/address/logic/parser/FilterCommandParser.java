package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new {@code FilterCommand} object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code FilterCommand}
     * and returns a {@code FilterCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHONE);

        if (!argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            throw new ParseException("Phone number is required.");
        }

        try {
            Phone phoneNumber = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            return new FilterCommand(phoneNumber);
        } catch (IllegalValueException ive) {
            throw new ParseException("Invalid phone number format.", ive);
        }
    }
}
