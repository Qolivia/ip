package saru;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Parses a create command and returns the Task to be added.
 * Throws SaruException if the command is invalid.
 */
public class Parser {

    // Aliases for command words (first token only).
    // Key: alias user types, Value: the official command word.
    private static final Map<String, String> COMMAND_ALIASES = Map.of(
            "t", "todo",
            "d", "deadline",
            "e", "event",
            "ls", "list",
            "rm", "delete",
            "q", "bye",
            "m", "mark",
            "um", "unmark",
            "f", "find"
    );

    private static String normalizeCommandWord(String userInput) {
        String trimmed = userInput.trim();

        if (trimmed.isEmpty()) {
            return trimmed;
        }

        String[] parts = trimmed.split("\\s+", 2);
        String commandWord = parts[0].toLowerCase(); // case-insensitive alias support
        String rest = (parts.length == 2) ? parts[1] : "";

        String normalized = COMMAND_ALIASES.getOrDefault(commandWord, commandWord);

        return rest.isEmpty() ? normalized : normalized + " " + rest;
    }

    /**
     * Normalizes the user input by converting supported command aliases
     * into their official command words.
     *
     * @param userInput Raw user input string.
     * @return Normalized command string.
     */
    public static String normalizeInput(String userInput) {
        return normalizeCommandWord(userInput);
    }

    private static final DateTimeFormatter STRICT_DATE =
            DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter STRICT_DATE_TIME =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm")
                    .withResolverStyle(ResolverStyle.STRICT);

    private static LocalDate parseDateOrThrow(String dateStr) throws SaruException {
        try {
            return LocalDate.parse(dateStr.trim(), STRICT_DATE);
        } catch (DateTimeParseException e) {
            throw new SaruException("Invalid date. Use yyyy-mm-dd, e.g. 2026-02-03");
        }
    }

    /**
     * Accepts:
     *  - yyyy-mm-dd
     *  - yyyy-mm-dd HHmm (24h)
     */
    private static LocalDateTime parseStartEndOrThrow(String s) throws SaruException {
        String trimmed = s.trim();
        try {
            if (trimmed.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return LocalDate.parse(trimmed, STRICT_DATE).atStartOfDay();
            }
            if (trimmed.matches("\\d{4}-\\d{2}-\\d{2}\\s+\\d{4}")) {
                return LocalDateTime.parse(trimmed, STRICT_DATE_TIME);
            }
            throw new SaruException("Invalid date/time. Use yyyy-mm-dd or yyyy-mm-dd HHmm.");
        } catch (DateTimeParseException e) {
            throw new SaruException("Invalid date/time. Use yyyy-mm-dd or yyyy-mm-dd HHmm.");
        }
    }


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
                throw new SaruException("Todo needs a description. Example: todo eat banana");
            }
            return new Todo(dscp);
        }

        if (input.equals("deadline") || input.startsWith("deadline ")) {
            String rest = input.length() > 8 ? input.substring(8).trim() : "";
            if (rest.isEmpty()) {
                throw new SaruException("Deadline needs a description. Example: deadline return book /by 2026-02-03");
            }
            if (!rest.contains(" /by ")) {
                throw new SaruException("Deadline format: deadline <task> /by <yyyy-mm-dd>");
            }

            String[] parts = rest.split(" /by ", 2);
            assert parts.length == 2 : "Deadline must have a description";
            String dscp = parts[0].trim();
            String byStr = parts[1].trim();

            if (dscp.isEmpty() || byStr.isEmpty()) {
                throw new SaruException("Deadline format: deadline <task> /by <time>");
            }

            LocalDate by = parseDateOrThrow(byStr);
            return new Deadline(dscp, by);
        }

        if (input.equals("event") || input.startsWith("event ")) {
            String rest = input.length() > 5 ? input.substring(5).trim() : "";
            if (rest.isEmpty()) {
                throw new SaruException("Event needs a description. " +
                        "Example: event house camp /from 2026-02-03 1200 /to 2026-02-05 1500");
            }
            if (!rest.contains(" /from ") || !rest.contains(" /to ")) {
                throw new SaruException("Event format: event <task> /from <yyyy-mm-dd HHmm> /to <yyyy-mm-dd HHmm>\n" +
                        "<HHmm> is optional!");
            }

            String[] first = rest.split(" /from ", 2);
            String dscp = first[0].trim();
            String afterFrom = first[1].trim();

            String[] second = afterFrom.split(" /to ", 2);
            String from = second[0].trim();
            String to = second[1].trim();

            if (dscp.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new SaruException("Event format: event <task> /from <yyyy-mm-dd HHmm> /to <yyyy-mm-dd HHmm>\n" +
                        "<HHmm> is optional!");
            }

            LocalDateTime fromDt = parseStartEndOrThrow(from);
            LocalDateTime toDt = parseStartEndOrThrow(to);

            if (!toDt.isAfter(fromDt)) {
                throw new SaruException("Invalid event \uD83D\uDE4A: /to must be after /from.");
            }

            return new Event(dscp, from, to);
        }

        throw new SaruException("I don't understand that command.\uD83D\uDE49");
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
