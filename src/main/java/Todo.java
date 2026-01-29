public class Todo extends Task{
    public Todo(String dscp) {
        super(dscp);
    }
    @Override
    public String toString() {
        return "[T] [" + getStatus() + "] " + dscp;
    }
}
