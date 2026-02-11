package saru;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the list of tasks currently managed by the application.
 * Provides basic operations such as add/remove/get and view accessors.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs a task list from an existing list.
     *
     * @param tasks Existing list of tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task at the given zero-based index.
     *
     * @param indexZeroBased Zero-based index of the task to remove.
     * @return The removed task.
     */
    public Task remove(int indexZeroBased) {
        return tasks.remove(indexZeroBased);
    }

    /**
     * Returns the task at the given zero-based index.
     *
     * @param indexZeroBased Zero-based index.
     * @return Task at the index.
     */
    public Task get(int indexZeroBased) {
        return tasks.get(indexZeroBased);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Size of task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying list (mutable). Used for saving.
     *
     * @return Backing list of tasks.
     */
    public List<Task> asList() {
        return tasks; // for Storage.save
    }

    /**
     * Returns an unmodifiable view of the tasks list.
     *
     * @return Unmodifiable list view.
     */
    public List<Task> asUnmodifiableList() {
        return Collections.unmodifiableList(tasks);
    }
}

