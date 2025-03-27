package seedu.address.logic.parser;

import java.time.LocalDateTime;

import seedu.address.logic.commands.TodayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code TodayCommand} object.
 */
public class TodayCommandParser implements Parser<TodayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TodayCommand
     * and returns a TodayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public TodayCommand parse(String args) throws ParseException {
        return new TodayCommand(LocalDateTime.now().toLocalDate());
    }
}
