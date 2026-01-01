import repository.FileTaskRepository;
import repository.InMemoryTaskRepository;
import service.TaskService;
import ui.ConsoleUI;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        InMemoryTaskRepository inMemoryTaskRepository = new InMemoryTaskRepository();
        TaskService taskService = new TaskService(inMemoryTaskRepository);
        ConsoleUI consoleUI = new ConsoleUI(scanner, taskService);

        consoleUI.run();
    }
}