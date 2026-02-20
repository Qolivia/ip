package saru;

/**
 * Represents a simple todo task without any date/time information.
 */
public class Todo extends Task {

    /**
     * Creates a Todo task with the given description.
     *
     * @param dscp Description of the task.
     */
    public Todo(String dscp) {
        super(dscp);
    }

    /**
     * Returns the string representation shown to the user.
     *
     * @return Formatted todo task string.
     */
    @Override
    public String toString() {
        return "[T] [" + getStatus() + "] " + dscp;
    }

    /**
     * Returns the storage-friendly representation of this task.
     *
     * @return Serialized string for file saving.
     */
    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + dscp;
    }

}
