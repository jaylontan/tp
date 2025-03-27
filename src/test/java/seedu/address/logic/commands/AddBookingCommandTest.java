package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class AddBookingCommandTest {

    @Test
    public void constructor_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddBookingCommand(null, LocalDateTime.now(), new HashSet<>(), "Dinner", 2));
    }

    @Test
    public void execute_validBooking_success() throws Exception {
        final Person person = new PersonBuilder().build();
        final ModelStubAcceptingBooking modelStub = new ModelStubAcceptingBooking(person);
        final LocalDateTime bookingDate = LocalDateTime.of(2025, 3, 30, 18, 0);
        final String remark = "Team Dinner";
        final int pax = 5;

        final AddBookingCommand command = new AddBookingCommand(person.getPhone(), bookingDate,
                new HashSet<>(), remark, pax);
        final CommandResult result = command.execute(modelStub);

        final Booking addedBooking = modelStub.getAddressBook().getBookingList().get(0);

        final Person updatedPerson = modelStub.getAddressBook().getPersonList().stream()
                .filter(p -> p.getPhone().equals(person.getPhone()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Person not found in AddressBook"));

        assertEquals(String.format(AddBookingCommand.MESSAGE_SUCCESS, Messages.format(addedBooking)),
                result.getFeedbackToUser());
        assertTrue(updatedPerson.getBookingIDs().contains(addedBooking.getBookingId()));
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        final ModelStubAcceptingBooking modelStub =
                new ModelStubAcceptingBooking(new PersonBuilder().build());
        final LocalDateTime now = LocalDateTime.now();
        final Phone nonexistentPhone = new Phone("99999999");

        final AddBookingCommand command = new AddBookingCommand(nonexistentPhone, now,
                new HashSet<>(), "Dinner", 4);

        assertThrows(CommandException.class,
                AddBookingCommand.MESSAGE_INVALID_PERSON, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        final Phone phone1 = new Phone("12345678");
        final Phone phone2 = new Phone("87654321");
        final LocalDateTime time = LocalDateTime.of(2025, 3, 30, 19, 0);

        final AddBookingCommand cmd1 = new AddBookingCommand(phone1, time, new HashSet<>(), "Dinner", 4);
        final AddBookingCommand cmd2 = new AddBookingCommand(phone1, time, new HashSet<>(), "Dinner", 4);
        final AddBookingCommand cmd3 = new AddBookingCommand(phone2, time, new HashSet<>(), "Dinner", 4);

        assertTrue(cmd1.equals(cmd1)); // same object
        assertTrue(cmd1.equals(cmd2)); // same values
        assertFalse(cmd1.equals(null)); // null
        assertFalse(cmd1.equals("Some string")); // different type
        assertFalse(cmd1.equals(cmd3)); // different phone
    }

    @Test
    public void toStringMethod() {
        final LocalDateTime bookingDate = LocalDateTime.of(2025, 3, 30, 20, 0);
        final AddBookingCommand command = new AddBookingCommand(TypicalPersons.ALICE.getPhone(), bookingDate,
                new HashSet<>(), "Birthday", 3);
        final String expected = AddBookingCommand.class.getCanonicalName()
                + "{phone=" + TypicalPersons.ALICE.getPhone()
                + ", bookingDate=" + bookingDate + "}";
        assertEquals(expected, command.toString());
    }

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBooking(Booking booking) {

        }

        @Override
        public ObservableList<Booking> getFilteredBookingList() {
            return null;
        }

        @Override
        public void updateFilteredBookingList(Predicate<Booking> predicate) {

        }

    }

    private class ModelStubAcceptingBooking extends ModelStub {
        private final AddressBook addressBook = new AddressBook();

        ModelStubAcceptingBooking(Person person) {
            addressBook.addPerson(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public void addBooking(Booking booking) {
            addressBook.addBooking(booking);
        }
    }
}
