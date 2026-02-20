package saru;

/**
 * Represents a generic task with a description and completion status.
 * Subclasses define how the task is displayed and saved.
 */
public abstract class Task {
    protected final String dscp;
    protected boolean isDone;

    /**
     * Creates a task with the given description.
     *
     * @param dscp Description of the task.
     */
    public Task(String dscp) {
        assert dscp != null : "Task description must not be null";
        this.dscp = dscp;
        this.isDone = false;
    }

    /**
     * Checks whether this task is marked as done.
     *
     * @return True if done, false otherwise.
     */
    public boolean checkIsDone() {
        return this.isDone;
    }

    /**
     * Returns the description of this task.
     *
     * @return Task description.
     */
    public String getDscp() {
        return this.dscp;
    }

    /**
     * Marks this task as done.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Unmarks this task (sets it back to not done).
     */
    public void unmarkDone() {
        this.isDone = false;
    }

    /**
     * Checks whether this task is marked as done.
     *
     * @return True if done, false otherwise.
     */
    public boolean isDone() { return isDone; }

    /**
     * Returns the status icon used in user-facing task strings.
     *
     * @return "X" if done, otherwise a blank space.
     */
    protected String getStatus() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the user-facing representation of this task.
     *
     * @return Task string for display.
     */
    @Override
    public abstract String toString();

    /**
     * Returns the storage-friendly representation of this task.
     *
     * @return Serialized string for file saving.
     */
    public abstract String toFileString();
}
