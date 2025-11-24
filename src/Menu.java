import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public class MenuItem {
        final String title;
        final Runnable handler;
        Manager manager;

        public MenuItem(String title, Runnable handler) {
            this.title = title;
            this.handler = handler;
        }
    }


    private Manager manager;
    private Scanner scanner;

    private List<MenuItem> menuItems;

    public Menu(Manager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
        this.menuItems = new ArrayList<>();
        createMenu();
        printMenu();
    }


    public void createMenu() {
        this.menuItems.add(new MenuItem("0) Выход", () -> exitAppWithCode(0)));
        this.menuItems.add(new MenuItem("1) Получение списка задач", this::getAllTasksByType));
        this.menuItems.add(new MenuItem("2) Удаление всех задач", this::removeAllTasks));
        this.menuItems.add(new MenuItem("3) Получить задачу по id", this::getAllTasksByType));
        this.menuItems.add(new MenuItem("4) Создание задачи", this::createTask));
        this.menuItems.add(new MenuItem("5) Удаление задачи по id", this::getAllTasksByType));
        this.menuItems.add(new MenuItem("6) Получение подзадач эпика", this::getAllTasksByType));
    }

    public void printMenu() {
        System.out.println("Выберите действие:");

        for (MenuItem item : this.menuItems) {
            System.out.println(item.title);
        }

        int input = this.scanner.nextInt();

        Runnable handler = this.menuItems.get(input).handler;
        handler.run();
    }

    private void printTaskTypeSelectionMenu() {
        System.out.println("Выберите тип: \n 1) Задача \n 2) Подзадача \n 3) Эпик");
    }

    private void printTaskNameSelectionMenu() {
        System.out.println("Введите название задачи:");
    }


    private void printTaskDescriptionSelectionMenu() {
        System.out.println("Введите описание задачи:");
    }

    private void printTaskStatusSelectionMenu() {
        System.out.println("Выберите статус задачи: \n 1) Новая \n 2) В работе \n 3) Выполнена");
    }

    public void getAllTasksByType() {
        printTaskTypeSelectionMenu();
        int input = this.scanner.nextInt();
        Manager.TaskType tasksType = manager.getTaskTypeByNumber(input);
        List<Task> tasks = manager.getTasksByType(tasksType);

        if (tasks.size() != 0) {
            System.out.println("Все задачи по типу " + tasksType + ": " + tasks);
        } else {
            System.out.println("Нет задач по типу " + tasksType);
        }

        printMenu();
    }

    public void removeAllTasks() {
        printTaskTypeSelectionMenu();
        int input = this.scanner.nextInt();
        Manager.TaskType tasksType = manager.getTaskTypeByNumber(input);
        manager.removeAllTasksByType(tasksType);

        System.out.println("Все задачи по типу " + tasksType + " удалены");

        printMenu();
    }

    public void createTask() {
        printTaskTypeSelectionMenu();
        int inputType = this.scanner.nextInt();
        Manager.TaskType tasksType = manager.getTaskTypeByNumber(inputType);
        this.scanner.nextLine();

        printTaskNameSelectionMenu();
        String inputName = this.scanner.nextLine();

        printTaskDescriptionSelectionMenu();
        String inputDescription = this.scanner.nextLine();

        Task newTask;

        switch (tasksType) {
            case TASK:
                newTask = new Task(Task.TaskStatus.NEW, inputName, inputDescription, manager.generateId());
                break;
            case EPIC:
                newTask = new Epic(Task.TaskStatus.NEW, inputName, inputDescription, manager.generateId());
                break;
            case SUBTASK:
                newTask = new Task(Task.TaskStatus.NEW, inputName, inputDescription, manager.generateId());
                break;
        }
    }

    public void exitAppWithCode(int code) {
        System.exit(code);
    }
}
