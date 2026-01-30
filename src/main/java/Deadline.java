public class Deadline extends Task{
    private final String by;

    public Deadline(String dscp, String by) {
        super(dscp);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D] [" + getStatus() + "] " + dscp + " (by: " + by + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + dscp + " | " + by;
    }

}
