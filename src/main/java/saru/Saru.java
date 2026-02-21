package saru;

/**
 * Main entry point of the Saru task manager application.
 * Coordinates user interaction (Ui), persistence (Storage), and task operations (TaskList).
 */
public class Saru {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;
    private boolean shouldExit = false;

    /**
     * Returns the welcome message displayed at application startup.
     *
     * @return the formatted welcome message string
     */
    public String getWelcomeMessage() {
        return "Hi! I'm Saru \uD83D\uDC12\n" +
                "Your little scheduling monkey.\n" +
                "What would you like to plan today?";
    }

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
                boolean shouldExit = handleCommand(input);
                if (shouldExit) {
                    break;
                }
            } catch (SaruException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    private boolean handleCommand(String input) throws SaruException {
        if (input.equals("bye")) {
            ui.showMessage("Bye. Hope to see you again soon!");
            return true;
        }

        if (input.equals("list")) {
            handleList();
            return false;
        }

        if (input.startsWith("mark ")) {
            handleMark(input);
            return false;
        }

        if (input.startsWith("unmark ")) {
            handleUnmark(input);
            return false;
        }

        if (input.startsWith("delete ")) {
            handleDelete(input);
            return false;
        }

        if (input.startsWith("find ")) {
            handleFind(input);
            return false;
        }

        handleCreate(input);
        return false;
    }

    private void handleList() {
        ui.showMessage("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    private void handleMark(String input) throws SaruException {
        int index = Parser.parseIndexOrThrow(input.substring(5));
        validateTaskNumber(index);

        Task t = tasks.get(index - 1);
        t.markDone();
        storage.save(tasks.asList());

        ui.showMessage("Nice! I've marked this task as done:");
        ui.showMessage(t.toString());
    }

    private void handleUnmark(String input) throws SaruException {
        int index = Parser.parseIndexOrThrow(input.substring(7));
        validateTaskNumber(index);

        Task t = tasks.get(index - 1);
        t.unmarkDone();
        storage.save(tasks.asList());

        ui.showMessage("OK, I've marked this task as not done yet:");
        ui.showMessage(t.toString());
    }

    private void handleDelete(String input) throws SaruException {
        int index = Parser.parseIndexOrThrow(input.substring(7));
        validateTaskNumber(index);

        Task removed = tasks.remove(index - 1);
        storage.save(tasks.asList());

        ui.showMessage("Noted. I've removed this task:");
        ui.showMessage("  " + removed);
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
    }

    private void handleFind(String input) throws SaruException {
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
    }

    private void handleCreate(String input) throws SaruException {
        Task newTask = Parser.parseCreateCommand(input);
        tasks.add(newTask);
        storage.save(tasks.asList());

        ui.showMessage("Got it. I've added this task:");
        ui.showMessage(newTask.toString());
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
    }

    private void validateTaskNumber(int index) throws SaruException {
        if (index < 1 || index > tasks.size()) {
            throw new SaruException("Invalid task number.");
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        input = Parser.normalizeInput(input);
        input = input.trim();

        try {
            if (input.isBlank()) {
                return "Oops! I need something to work with \uD83D\uDE49\n" +
                        "What would you like to do?";
            }

            if (input.equals("bye")) {
                shouldExit = true;
                storage.save(tasks.asList());
                return "Bye. Hope to see you again soon!";
            }

            if (input.equals("list")) {
                StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
                }
                return sb.toString().trim();
            }

            if (input.startsWith("mark ")) {
                int index = Parser.parseIndexOrThrow(input.substring(5));
                validateTaskNumber(index);
                Task t = tasks.get(index - 1);

                if (t.isDone()) {
                    return "This task is already marked as done:\n" + t;
                }

                t.markDone();
                storage.save(tasks.asList());
                return "Nice! I've marked this task as done:\n" + t;
            }

            if (input.startsWith("unmark ")) {
                int index = Parser.parseIndexOrThrow(input.substring(7));
                validateTaskNumber(index);
                Task t = tasks.get(index - 1);

                if (!t.isDone()) {
                    return "This task is already marked as not done:\n" + t;
                }

                t.unmarkDone();
                storage.save(tasks.asList());
                return "OK, I've marked this task as not done yet:\n" + t;
            }

            if (input.startsWith("delete ")) {
                int index = Parser.parseIndexOrThrow(input.substring(7));
                validateTaskNumber(index);
                Task removed = tasks.remove(index - 1);
                storage.save(tasks.asList());
                return "Noted. I've removed this task:\n  " + removed
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
            }

            if (input.startsWith("find ")) {
                String keyword = input.substring(5).trim();
                if (keyword.isEmpty()) {
                    throw new SaruException("Please provide a keyword to search.");
                }

                StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
                int count = 0;
                for (int i = 0; i < tasks.size(); i++) {
                    Task t = tasks.get(i);
                    if (t.getDscp().contains(keyword)) {
                        count++;
                        sb.append(count).append(". ").append(t).append("\n");
                    }
                }
                if (count == 0) {
                    return "No matching tasks found.";
                }
                return sb.toString().trim();
            }

            // create commands
            Task newTask = Parser.parseCreateCommand(input);
            tasks.add(newTask);
            storage.save(tasks.asList());
            return "Got it. I've added this task:\n" + newTask
                    + "\nNow you have " + tasks.size() + " tasks in the list.";

        } catch (SaruException e) {
            return e.getMessage();
        }
    }

    /**
     * Returns whether the application should terminate.
     *
     * @return true if Saru is instructed to exit, false otherwise
     */
    public boolean shouldExit() {
        return shouldExit;
    }

    public static void main(String[] args) {
        new Saru().run();
    }
}
