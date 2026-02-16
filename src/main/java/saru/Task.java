package saru;

public abstract class Task {
    protected final String dscp;
    protected boolean isDone;

    public Task(String dscp) {
        assert dscp != null : "Task description must not be null";
        this.dscp = dscp;
        this.isDone = false;
    }

    public boolean checkIsDone() {
        return this.isDone;
    }

    public String getDscp() {
        return this.dscp;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmarkDone() {
        this.isDone = false;
    }

    protected String getStatus() {
        return isDone ? "X" : " ";
    }

    @Override
    public abstract String toString();

    public abstract String toFileString();
}
