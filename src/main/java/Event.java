public class Event extends Task{
    private final String from;
    private final String to;

    public Event(String dscp, String from, String to) {
        super(dscp);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E] [" + getStatus() + "] " + dscp + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + dscp + " | " + from + " | " + to;
    }

}
