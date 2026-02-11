package saru;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    public void add_increasesSize_andGetReturnsSameTask() {
        TaskList list = new TaskList();
        Task t = new Todo("read book");

        list.add(t);

        assertEquals(1, list.size());
        assertSame(t, list.get(0));
    }

    @Test
    public void remove_returnsRemovedTask_andDecreasesSize() {
        TaskList list = new TaskList();
        Task t = new Todo("gym");
        list.add(t);

        Task removed = list.remove(0);

        assertSame(t, removed);
        assertEquals(0, list.size());
    }
}
