package saru;

public class Todo extends Task{
    public Todo(String dscp) {
        super(dscp);
    }
    @Override
    public String toString() {
        return "[T] [" + getStatus() + "] " + dscp;
    }

    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + dscp;
    }

}
