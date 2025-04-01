package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS;
import static seedu.address.logic.commands.EditBookingCommand.MESSAGE_PAST_BOOKING_WARNING;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
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
import seedu.address.testutil.PersonBuilder;

public class EditBookingCommandTest {

    @Test
    public void execute_validBookingEdit_success() throws Exception {
        Person person = new PersonBuilder().build();
        ModelStubAcceptingBooking modelStub = new ModelStubAcceptingBooking(person);
        Booking booking = new Booking(person, LocalDateTime.now().plusDays(1), "Dinner", 4);
        modelStub.addBooking(booking);

        HashMap<String, Object> fieldsToEdit = new HashMap<>();
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(2); // Valid future date
        fieldsToEdit.put("bookingDateTime", newDateTime);

        EditBookingCommand editBookingCommand = new EditBookingCommand(booking.getBookingId(), fieldsToEdit);
        CommandResult commandResult = editBookingCommand.execute(modelStub);

        assertEquals(String.format(MESSAGE_EDIT_BOOKING_SUCCESS, Messages.format(booking)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_pastDateEdit_showsWarning() throws Exception {
        Person person = new PersonBuilder().build();
        ModelStubAcceptingBooking modelStub = new ModelStubAcceptingBooking(person);
        Booking booking = new Booking(person, LocalDateTime.now().plusDays(1), "Dinner", 4);
        modelStub.addBooking(booking);

        HashMap<String, Object> fieldsToEdit = new HashMap<>();
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1); // A date in the past
        fieldsToEdit.put("bookingDateTime", pastDate);

        EditBookingCommand editBookingCommand = new EditBookingCommand(booking.getBookingId(), fieldsToEdit);
        CommandResult commandResult = editBookingCommand.execute(modelStub);

        String expectedMessage = MESSAGE_PAST_BOOKING_WARNING
                + String.format(MESSAGE_EDIT_BOOKING_SUCCESS, Messages.format(booking));
        assertEquals(expectedMessage.trim(), commandResult.getFeedbackToUser().trim());
    }

    @Test
    public void execute_nonExistentBookingId_throwsCommandException() {
        Person person = new PersonBuilder().build();
        ModelStubAcceptingBooking modelStub = new ModelStubAcceptingBooking(person);

        HashMap<String, Object> fieldsToEdit = new HashMap<>();
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(2);
        fieldsToEdit.put("bookingDateTime", newDateTime);

        EditBookingCommand editBookingCommand = new EditBookingCommand(999, fieldsToEdit); // Non-existent booking ID

        assertThrows(CommandException.class, () -> editBookingCommand.execute(modelStub));
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
        public boolean hasPersonByPhoneCheck(Person person) {
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

    private class ModelStubAcceptingBooking extends EditBookingCommandTest.ModelStub {
        private final AddressBook addressBook = new AddressBook();
        private final ObservableList<Booking> filteredBookings = javafx.collections.FXCollections.observableArrayList();

        ModelStubAcceptingBooking(Person person) {
            addressBook.addPerson(person);
            filteredBookings.setAll(addressBook.getBookingList()); // Initialize with current bookings
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public void addBooking(Booking booking) {
            addressBook.addBooking(booking);
            filteredBookings.add(booking); // Add the booking to the observable list
        }

        @Override
        public ObservableList<Booking> getFilteredBookingList() {
            return filteredBookings; // Return the observable list to avoid NullPointerException
        }

        @Override
        public void updateFilteredBookingList(Predicate<Booking> predicate) {
            filteredBookings.setAll(addressBook.getBookingList().filtered(predicate)); // Apply predicate filter
        }
    }
}
