package saru;

/**
 * Main entry point of the Saru task manager application.
 * Coordinates user interaction (Ui), persistence (Storage), and task operations (TaskList).
 */
public class Saru {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs a Saru application with default UI and storage.
     * Loads any previously saved tasks from storage.
     */
    public Saru() {
        this.ui = new Ui();
        this.storage = new Storage();
        this.tasks = new TaskList(storage.load());
    }

    /**
     * Runs the main command loop until the user exits with "bye".
     * Commands are read from Ui and executed by delegating to other classes.
     */
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
                    int index = Parser.parseIndexOrThrow(input.substring(5));
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
                    int index = Parser.parseIndexOrThrow(input.substring(7));
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
                    int index = Parser.parseIndexOrThrow(input.substring(7));
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

                if (input.startsWith("find ")) {
                    String keyword = input.substring(5).trim();

                    if (keyword.isEmpty()) {
                        throw new SaruException("Please provide a keyword to search.");
                    }

                    ui.showMessage("Here are the matching tasks in your list:");

                    int count = 0;
                    for (int i = 0; i < tasks.size(); i++) {
                        Task t = tasks.get(i);

                        if (t.getDscp().contains(keyword)) {
                            count++;
                            System.out.println(count + ". " + t);
                        }
                    }

                    if (count == 0) {
                        ui.showMessage("No matching tasks found.");
                    }

                    continue;
                }


                Task newTask = Parser.parseCreateCommand(input);
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

    /**
     * Launches the application.
     */
    public static void main(String[] args) {
        new Saru().run();
    }
}
