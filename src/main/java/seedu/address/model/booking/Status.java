package seedu.address.model.booking;

/**
 * Represents the status of a booking.
 */
public enum Status {
    UPCOMING,
    CANCELLED,
    COMPLETED;

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
}
