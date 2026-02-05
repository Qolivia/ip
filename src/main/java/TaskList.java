import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int indexZeroBased) {
        return tasks.remove(indexZeroBased);
    }

    public Task get(int indexZeroBased) {
        return tasks.get(indexZeroBased);
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> asList() {
        return tasks; // for Storage.save
    }

    public List<Task> asUnmodifiableList() {
        return Collections.unmodifiableList(tasks);
    }
}

