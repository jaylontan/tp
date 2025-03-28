package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOKING_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkCommand;
import seedu.address.model.booking.Status;

public class MarkCommandParserTest {

    private MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        int bookingId = 1;
        Status status = Status.COMPLETED;
        String userInput = " " + PREFIX_BOOKING_ID + bookingId + " " + PREFIX_STATUS + status.name();

        MarkCommand expectedCommand = new MarkCommand(bookingId, status);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingBookingId_failure() {
        String userInput = " " + PREFIX_STATUS + "COMPLETED";
        assertParseFailure(parser, userInput, String.format(
                seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingStatus_failure() {
        String userInput = " " + PREFIX_BOOKING_ID + "1";
        assertParseFailure(parser, userInput, String.format(
                seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidBookingId_failure() {
        String userInput = " " + PREFIX_BOOKING_ID + "abc" + " " + PREFIX_STATUS + "COMPLETED";
        assertParseFailure(parser, userInput, "Booking ID must be a valid integer.");
    }

    @Test
    public void parse_invalidStatus_failure() {
        String userInput = " " + PREFIX_BOOKING_ID + "1" + " " + PREFIX_STATUS + "invalidStatus";
        assertParseFailure(parser, userInput, MarkCommand.MESSAGE_INVALID_STATUS);
    }

    @Test
    public void parse_extraPreamble_failure() {
        String userInput = "randomText " + PREFIX_BOOKING_ID + "1" + " " + PREFIX_STATUS + "COMPLETED";
        assertParseFailure(parser, userInput, String.format(
                seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }
}