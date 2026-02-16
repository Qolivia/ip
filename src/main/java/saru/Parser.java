package saru;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses a create command and returns the Task to be added.
 * Throws SaruException if the command is invalid.
 */
public class Parser {

    /**
     * Parses a create command (todo, deadline, event) and returns the Task to be added.
     *
     * @param input Full user input string.
     * @return A Task instance created from the command.
     * @throws SaruException If the command format is invalid.
     */
    public static Task parseCreateCommand(String input) throws SaruException {
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
            assert parts.length == 2 : "Command must have a description";
            String dscp = parts[0].trim();
            String byStr = parts[1].trim();

            if (dscp.isEmpty() || byStr.isEmpty()) {
                throw new SaruException("Deadline format: deadline <task> /by <time>");
            }

            try {
                return new Deadline(dscp, LocalDate.parse(byStr));
            } catch (DateTimeParseException e) {
                throw new SaruException("Invalid date. Use yyyy-mm-dd, e.g. 2019-10-15");
            }
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

    /**
     * Parses an integer index from a string.
     *
     * @param s String that should contain an integer.
     * @return Parsed integer value.
     * @throws SaruException If the string is not a valid integer.
     */
    public static int parseIndexOrThrow(String s) throws SaruException {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw new SaruException("Please provide a valid task number.");
        }
    }
}
