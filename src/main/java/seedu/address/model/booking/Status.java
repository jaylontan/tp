package seedu.address.model.booking;

/**
 * Represents the status of a booking.
 */
public enum Status {
    UPCOMING,
    CANCELLED,
    COMPLETED;

    public static final String MESSAGE_CONSTRAINTS = "Status should be either 'Upcoming', 'Cancelled' or 'Completed'";

    /**
     * Returns the string representation of the status.
     *
     * @return String representation of the status.
     */
    public String toString() {
        return switch (this) {
        case UPCOMING -> "Upcoming";
        case CANCELLED -> "Cancelled";
        case COMPLETED -> "Completed";
        };
    }

    /**
     * Returns the status from the string representation.
     *
     * @param status String representation of the status.
     * @return Status.
     */
    public static Status fromString(String status) {
        return switch (status.toLowerCase()) {
        case "upcoming" -> UPCOMING;
        case "cancelled" -> CANCELLED;
        case "completed" -> COMPLETED;
        default -> throw new IllegalArgumentException("Invalid status");
        };
    }

    /**
     * Parses status from string and returns True if valid, false otherwise.
     *
     * @param status String representation of the status.
     * @return boolean True if status is valid, False otherwise.
     */
    public static boolean isValidStatus(String status) {
        try {
            fromString(status);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
