package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Status;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MarkCommandTest {

    @Test
    public void execute_validBookingId_success() throws Exception {
        Person person = new PersonBuilder().build();
        Booking booking = new Booking(person, LocalDateTime.now().plusDays(1), "Dinner", 2);
        booking.setBookingPerson(person);

        ModelStubWithBookings modelStub = new ModelStubWithBookings(booking);

        MarkCommand markCommand = new MarkCommand(booking.getBookingId(), Status.COMPLETED);
        CommandResult result = markCommand.execute(modelStub);

        assertEquals(String.format(MarkCommand.MESSAGE_SUCCESS, booking.getBookingId(), Status.COMPLETED),
                result.getFeedbackToUser());
        assertEquals(Status.COMPLETED, modelStub.getAddressBook().getBookingList().get(0).getStatus());
    }

    @Test
    public void execute_invalidBookingId_throwsCommandException() {
        ModelStubWithBookings modelStub = new ModelStubWithBookings(); // no bookings
        MarkCommand markCommand = new MarkCommand(99, Status.CANCELLED);
        assertThrows(CommandException.class,
                String.format(MarkCommand.MESSAGE_INVALID_ID, 99), () -> markCommand.execute(modelStub));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        MarkCommand command = new MarkCommand(1, Status.UPCOMING);
        assertEquals(command, command);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        MarkCommand command = new MarkCommand(1, Status.UPCOMING);
        assertNotEquals(command, "Some String");
    }

    @Test
    public void equals_differentBookingId_returnsFalse() {
        MarkCommand cmd1 = new MarkCommand(1, Status.UPCOMING);
        MarkCommand cmd2 = new MarkCommand(2, Status.UPCOMING);
        assertNotEquals(cmd1, cmd2);
    }

    @Test
    public void equals_differentStatus_returnsFalse() {
        MarkCommand cmd1 = new MarkCommand(1, Status.UPCOMING);
        MarkCommand cmd2 = new MarkCommand(1, Status.CANCELLED);
        assertNotEquals(cmd1, cmd2);
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

        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {

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

        @Override
        public Predicate<Person> getCurrentPersonPredicate() {
            return null;
        }

        @Override
        public Predicate<Booking> getCurrentBookingPredicate() {
            return null;
        }

        @Override
        public boolean isBookingListFiltered() {
            return false;
        }
    }

    private class ModelStubWithBookings extends ModelStub {
        private final AddressBook addressBook = new AddressBook();

        ModelStubWithBookings(Booking... bookings) {
            Person dummyPerson = new PersonBuilder().build();
            addressBook.addPerson(dummyPerson);
            for (Booking booking : bookings) {
                booking.setBookingPerson(dummyPerson);
                addressBook.addBooking(booking);
            }
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }
    }
}
