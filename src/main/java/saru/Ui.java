package saru;

import java.util.Scanner;

/**
 * Represents the user interface component of Saru.
 *
 * Handles all interactions with the user via the command line,
 * including displaying messages and reading user input.
 */
public class Ui {
    private final Scanner sc;

    /**
     * Creates a Ui instance with a Scanner attached to System.in.
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Saru");
        System.out.println("What can I do for you?");
    }

    /**
     * Reads a command entered by the user.
     *
     * @return the trimmed user input string
     */
    public String readCommand() {
        return sc.nextLine().trim();
    }

    /**
     * Displays a normal message to the user.
     *
     * @param message the message to be shown
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to be shown
     */
    public void showError(String message) {
        System.out.println(message);
    }
}
