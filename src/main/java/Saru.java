import java.util.Scanner;

public class Saru {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] tasks = new String[100];
        int cnt = 0;
;        System.out.println("Hello! I'm Saru");
        System.out.println("What can I do for you?");

        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }
            if (input.equals("list")) {
                for (int i = 0; i < cnt; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                continue;
            }
            tasks[cnt] = input;
            cnt++;

            System.out.println("added: " + input);
        }
    }
}
