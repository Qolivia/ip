package saru;

import java.time.LocalDate;

/**
 * Represents a task with a deadline date.
 * A Deadline task is considered due by a specific {@link LocalDate}.
 */
public class Deadline extends Task {
    private LocalDate by;

    /**
     * Creates a Deadline task with a description and a due date.
     *
     * @param dscp Description of the task.
     * @param by Due date of the task.
     */
    public Deadline(String dscp, LocalDate by) {
        super(dscp);
        this.by = by;
    }

    /**
     * Returns the string representation shown to the user.
     *
     * @return Formatted deadline task string.
     */
    @Override
    public String toString() {
        return "[D] [" + getStatus() + "] " + dscp + " (by: " + by + ")";
    }

    /**
     * Returns the storage-friendly representation of this task.
     *
     * @return Serialized string for file saving.
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + dscp + " | " + by;
    }

}
