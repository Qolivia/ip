import java.util.Scanner;

public class Saru {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int cnt = 0;

;       System.out.println("Hello! I'm Saru");
        System.out.println("What can I do for you?");

        while (true) {
            String input = sc.nextLine().trim();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < cnt; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                continue;
            }

            if (input.startsWith("mark ")) {
                int index = parseIndex(input.substring(5));
                if (index < 1 || index > cnt) {
                    System.out.println("Invalid task number.");
                    continue;
                }

                Task t = tasks[index - 1];
                t.markDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(t);
                continue;
            }

            if (input.startsWith("unmark ")) {
                int index = parseIndex(input.substring(7));
                if (index < 1 || index > cnt) {
                    System.out.println("Invalid task number.");
                    continue;
                }

                Task t = tasks[index - 1];
                t.unmarkDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(t);
                continue;
            }
            if (input.startsWith("todo ")) {
                tasks[cnt++] = new Todo(input.substring(5));
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ");
                tasks[cnt++] = new Deadline(parts[0], parts[1]);
            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split(" /from | /to ");
                tasks[cnt++] = new Event(parts[0], parts[1], parts[2]);
            } else {
                System.out.println("I don't understand that command.");
                continue;
            }

            System.out.println("Got it. I've added this task:");
            System.out.println(tasks[cnt - 1]);
            System.out.println("Now you have " + cnt + " tasks in the list.");
        }
    }

    private static int parseIndex(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
