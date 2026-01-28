public class Task {
    private final String dscp;
    private boolean isDone;

    public Task(String dscp) {
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

    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + dscp;
    }
}
