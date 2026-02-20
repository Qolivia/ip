package saru;

/**
 * Represents an event task with a start and end time/date.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates an Event task with a description, start, and end.
     *
     * @param dscp Description of the event.
     * @param from Start time/date string.
     * @param to End time/date string.
     */
    public Event(String dscp, String from, String to) {
        super(dscp);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the string representation shown to the user.
     *
     * @return Formatted event task string.
     */
    @Override
    public String toString() {
        return "[E] [" + getStatus() + "] " + dscp + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns the storage-friendly representation of this task.
     *
     * @return Serialized string for file saving.
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + dscp + " | " + from + " | " + to;
    }

}
