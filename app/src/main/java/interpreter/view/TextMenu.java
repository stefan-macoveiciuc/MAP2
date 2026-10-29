package interpreter.view;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {

    private final Map<String, Command> commands;

    public TextMenu() {
        commands = new LinkedHashMap<>();
    }

    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }

    private void printMenu() {

        System.out.println();
        System.out.println("===== Toy Language Interpreter =====");

        for (Command command : commands.values()) {
            System.out.println(
                    command.getKey() + ". " + command.getDescription()
            );
        }

        System.out.println("====================================");
    }

    public void show() {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            printMenu();

            System.out.print("Choose an option: ");

            if (!scanner.hasNextLine()) {
                return;
            }

            String key = scanner.nextLine().trim();

            Command command = commands.get(key);

            if (command == null) {
                System.out.println(
                        "Invalid option. Please try again."
                );
                continue;
            }

            command.execute();
        }
    }
}