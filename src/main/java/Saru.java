import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Saru {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    public Saru() {
        this.ui = new Ui();
        this.storage = new Storage();
        this.tasks = new TaskList(storage.load());
    }

    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            try {
                if (input.equals("bye")) {
                    ui.showMessage("Bye. Hope to see you again soon!");
                    break;
                }

                if (input.equals("list")) {
                    ui.showMessage("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    continue;
                }

                if (input.startsWith("mark ")) {
                    int index = parseIndexOrThrow(input.substring(5));
                    if (index < 1 || index > tasks.size()) {
                        throw new SaruException("Invalid task number.");
                    }

                    Task t = tasks.get(index - 1);
                    t.markDone();
                    storage.save(tasks.asList());

                    ui.showMessage("Nice! I've marked this task as done:");
                    ui.showMessage(t.toString());
                    continue;
                }

                if (input.startsWith("unmark ")) {
                    int index = parseIndexOrThrow(input.substring(7));
                    if (index < 1 || index > tasks.size()) {
                        throw new SaruException("Invalid task number.");
                    }

                    Task t = tasks.get(index - 1);
                    t.unmarkDone();
                    storage.save(tasks.asList());

                    ui.showMessage("OK, I've marked this task as not done yet:");
                    ui.showMessage(t.toString());
                    continue;
                }

                if (input.startsWith("delete ")) {
                    int index = parseIndexOrThrow(input.substring(7));
                    if (index < 1 || index > tasks.size()) {
                        System.out.println("Invalid task number.");
                        continue;
                    }

                    Task removed = tasks.remove(index - 1);
                    storage.save(tasks.asList());

                    ui.showMessage("Noted. I've removed this task:");
                    ui.showMessage("  " + removed);
                    ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
                    continue;
                }

                Task newTask = parseCreateCommand(input);
                tasks.add(newTask);
                storage.save(tasks.asList());

                ui.showMessage("Got it. I've added this task:");
                ui.showMessage(newTask.toString());
                ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");

            } catch (SaruException e) {
                ui.showError(e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        new Saru().run();
    }
    /**
     * Parses create commands and returns the Task to be added.
     * Throws SaruException if the command is invalid.
     */
    private static Task parseCreateCommand(String input) throws SaruException {

        if (input.equals("todo") || input.startsWith("todo ")) {
            String dscp = input.length() > 4 ? input.substring(4).trim() : "";
            if (dscp.isEmpty()) {
                throw new SaruException("Todo needs a description. Example: todo borrow book");
            }
            return new Todo(dscp);
        }


        if (input.equals("deadline") || input.startsWith("deadline ")) {
            String rest = input.length() > 8 ? input.substring(8).trim() : "";
            if (rest.isEmpty()) {
                throw new SaruException("Deadline needs a description. Example: deadline return book /by Sunday");
            }
            if (!rest.contains(" /by ")) {
                throw new SaruException("Deadline format: deadline <task> /by <yyyy-mm-dd>");
            }

            String[] parts = rest.split(" /by ", 2);
            String dscp = parts[0].trim();
            String byStr = parts[1].trim();

            if (dscp.isEmpty() || byStr.isEmpty()) {
                throw new SaruException("Deadline format: deadline <task> /by <time>");
            }

            LocalDate by;
            try {
                by = LocalDate.parse(byStr); // yyyy-mm-dd
            } catch (DateTimeParseException e) {
                throw new SaruException("Invalid date. Use yyyy-mm-dd, e.g. 2019-10-15");
            }

            return new Deadline(dscp, by);
        }

        if (input.equals("event") || input.startsWith("event ")) {
            String rest = input.length() > 5 ? input.substring(5).trim() : "";
            if (rest.isEmpty()) {
                throw new SaruException("Event needs a description. Example: event project meeting /from 2pm /to 4pm");
            }
            if (!rest.contains(" /from ") || !rest.contains(" /to ")) {
                throw new SaruException("Event format: event <task> /from <start> /to <end>");
            }

            String[] first = rest.split(" /from ", 2);
            String dscp = first[0].trim();
            String afterFrom = first[1].trim();

            String[] second = afterFrom.split(" /to ", 2);
            String from = second[0].trim();
            String to = second[1].trim();

            if (dscp.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new SaruException("Event format: event <task> /from <start> /to <end>");
            }
            return new Event(dscp, from, to);
        }

        throw new SaruException("I don't understand that command.");
    }
    private static int parseIndexOrThrow(String s) throws SaruException {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw new SaruException("Please provide a valid task number.");
        }
    }
}
