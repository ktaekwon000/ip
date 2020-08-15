import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final String LINE = "    ____________________________________________________________";
    private static final String WELCOME_MESSAGE = "Hello, I'm Duke, your personal assistant!\n"
        + "     What can I do for you?";
    private static final String GOODBYE_MESSAGE = "Bye. Hope to see you again soon! :)";

    private static ArrayList<Task> list = new ArrayList<>();

    private static String makeWrappedString(String txt) {
        return LINE + "\n     " + txt + "\n" + LINE;
    }

    private static void addToList(Task task) {
        list.add(task);
        System.out.println(LINE
            + "\n     Sure, I've added this task to your list:\n"
            + "       " + task
            + "\n     You now have " + list.size() + " task(s) in the list!\n"
            + LINE);
    }

    public static void main(String[] args) {
        System.out.println(makeWrappedString(WELCOME_MESSAGE));

        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        while (!input.equals("bye")) {
            try {
                switch (input.split(" ")[0]) {
                case "todo":
                    if (input.length() <= 5 || input.split(" ").length < 2) {
                        throw new EmptyDescriptionException("The description of a todo cannot be empty.");
                    }
                    addToList(new Todo(input.substring(5)));
                    break;
                case "deadline":
                    int indexOfBy = input.indexOf(" /by ");
                    if (input.length() <= 9 || input.split(" ").length < 2) {
                        throw new EmptyDescriptionException("The description of a deadline cannot be empty.");
                    }
                    addToList(new Deadline(input.substring(9, indexOfBy), input.substring(indexOfBy + 5)));
                    break;
                case "event":
                    int indexOfAt = input.indexOf(" /at ");
                    if (input.length() <= 6 || input.split(" ").length < 2) {
                        throw new EmptyDescriptionException("The description of an event cannot be empty.");
                    }
                    addToList(new Event(input.substring(6, indexOfAt), input.substring(indexOfAt + 5)));
                    break;
                case "list":
                    System.out.println(LINE + "\n     Here are the tasks in your list:");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println("     " + (i + 1) + ": " + list.get(i));
                    }
                    System.out.println(LINE);
                    break;
                case "done":
                    Task doneTask = list.get(Integer.parseInt(input.split(" ")[1]) - 1);
                    doneTask.setDone(true);
                    System.out.println(
                        makeWrappedString("Great Job! I've marked this task as done:\n       " + doneTask));
                    break;
                default:
                    System.out.println(makeWrappedString("Check your command again!\n     "
                        + "Your command should either start with todo, deadline, event, list or done."));
                    break;
                }
            } catch (EmptyDescriptionException e) {
                System.out.println(makeWrappedString("Error! You have an empty description.\n     "
                    + e.getMessage()));
            }
            input = sc.nextLine();
        }

        System.out.println(makeWrappedString(GOODBYE_MESSAGE));
    }
}
