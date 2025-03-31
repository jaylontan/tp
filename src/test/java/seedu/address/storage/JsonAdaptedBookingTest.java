package seedu.address.storage;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalBookings.BENSONS_BOOKING;

public class JsonAdaptedBookingTest {
    private static final String INVALID_TIME = "INVALID DATE";
    private static final String INVALID_STATUS = "INVALID";
    private static final int INVALID_PAX = -1;

    private static final Person VALID_PERSON = BENSON;
    private static final String VALID_TIME = BENSONS_BOOKING.getBookingDateTime().toString();
    private static final String VALID_STATUS = BENSONS_BOOKING.getStatus().toString();
    private static final int VALID_PAX = BENSONS_BOOKING.getPax();
    private static final String VALID_REMARKS = BENSONS_BOOKING.getRemarks();

    @Test
    public void toModelType_validBookingDetails_returnsBooking() throws Exception {
        JsonAdaptedBooking booking = new JsonAdaptedBooking(BENSONS_BOOKING);
        Booking modelTypeBooking = booking.toModelType();
        modelTypeBooking.setBookingPerson(BENSON);
        assertEquals(BENSONS_BOOKING, modelTypeBooking);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(0, INVALID_TIME, INVALID_TIME, VALID_STATUS, VALID_REMARKS, VALID_PAX);
        String expectedMessage = "Text 'INVALID DATE' could not be parsed at index 0";
        assertThrows(java.time.format.DateTimeParseException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(0, VALID_TIME, VALID_TIME, INVALID_STATUS, VALID_REMARKS, VALID_PAX);
        String expectedMessage = "Status should be either 'Upcoming', 'Cancelled' or 'Completed'";
        assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_invalidPax_throwsIllegalValueException() {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(0, VALID_TIME, VALID_TIME, VALID_STATUS, VALID_REMARKS, INVALID_PAX);
        String expectedMessage = "Pax should be a non-zero positive integer less than 10000";
        assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    /*
    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, true, VALID_BOOKINGIDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        true, VALID_BOOKINGIDS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TAGS, false, VALID_BOOKINGIDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS,
                        true, VALID_BOOKINGIDS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TAGS, false, VALID_BOOKINGIDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags,
                        true, VALID_BOOKINGIDS);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
    */
}
