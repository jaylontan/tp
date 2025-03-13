package seedu.address.model.booking;

public enum Status {
    UPCOMING,
    CANCELLED,
    COMPLETED;

    public String toString() {
        return switch (this) {
            case UPCOMING -> "Upcoming";
            case CANCELLED -> "Cancelled";
            case COMPLETED -> "Completed";
        };
    }

    public static Status fromString(String status) {
        return switch (status.toLowerCase()) {
            case "upcoming" -> UPCOMING;
            case "cancelled" -> CANCELLED;
            case "completed" -> COMPLETED;
            default -> throw new IllegalArgumentException("Invalid status");
        };
    }
}
