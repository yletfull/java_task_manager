import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Menu {
    static class MenuItem {
        final String title;
        final Runnable handler;

        public MenuItem(String title, Runnable handler) {
            this.title = title;
            this.handler = handler;
        }
    }


    private Manager manager;
    private Scanner scanner;
    private boolean isRunnable = true;

    private List<MenuItem> menuItems;

    public void run() {
        while (isRunnable) {
            printMenu();
        }
    }

    public Menu(Manager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
        this.menuItems = new ArrayList<>();
        createMenu();
    }


    public void createMenu() {
        this.menuItems.add(new MenuItem("0) Выход", () -> exitAppWithCode(0)));
        this.menuItems.add(new MenuItem("1) Получение списка задач", this::getAllTasksByType));
        this.menuItems.add(new MenuItem("2) Удаление всех задач", this::removeAllTasks));
        this.menuItems.add(new MenuItem("3) Получить задачу по id", this::getAllTasksByType));
        this.menuItems.add(new MenuItem("4) Создание задачи", this::createTask));
        this.menuItems.add(new MenuItem("5) Удаление задачи по id", this::getAllTasksByType));
        this.menuItems.add(new MenuItem("6) Получение подзадач эпика", this::getAllTasksByType));
        this.menuItems.add(new MenuItem("7) Сохранить файл", this::saveFile));
        this.menuItems.add(new MenuItem("8) Загрузить файл", this::loadFile));
    }

    public void printMenu() {
        System.out.println("Выберите действие:");

        for (MenuItem item : this.menuItems) {
            System.out.println(item.title);
        }

        int input = this.scanner.nextInt();

        if (input >= 0 && input < this.menuItems.size()) {
            Runnable handler = this.menuItems.get(input).handler;
            handler.run();
        } else {
            System.out.println("Неверный пункт меню");
        }
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

    private void printTaskSuccessAdd() {
        System.out.println("Задача успешно добавлена");
    }

    private void printGetEpicSubtasksIds() {
        System.out.println("Введите id задач, включенных в Epic");
    }

    private void printGetParentEpicId() {
        System.out.println("Введите id Epic");
    }

    private void saveFile() {
        try {
            this.manager.saveFile(Path.of("data", "tasks.bin"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadFile() {
        try {
            this.manager.loadFile(Path.of("data", "tasks.bin"));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void getAllTasksByType() {
        printTaskTypeSelectionMenu();
        int input = this.scanner.nextInt();
        Manager.TaskType tasksType = getTaskTypeByNumber(input);
        List<Task> tasks = manager.getTasksByType(tasksType);

        if (tasks.size() != 0) {
            System.out.println("Все задачи по типу " + tasksType + ": " + tasks);
        } else {
            System.out.println("Нет задач по типу " + tasksType);
        }
    }

    public void removeAllTasks() {
        printTaskTypeSelectionMenu();
        int input = this.scanner.nextInt();
        Manager.TaskType tasksType = getTaskTypeByNumber(input);
        manager.removeAllTasksByType(tasksType);
        System.out.println("Все задачи по типу " + tasksType + " удалены");
    }

    public List<Subtask> getResultEpicSubtasks() {
        printGetEpicSubtasksIds();
        String inputTasksIds = this.scanner.nextLine();
        List<Subtask> resultSubtasks = new ArrayList<>();
        Set<String> parsedTasksIds = new HashSet<>(Arrays.asList(inputTasksIds.split("\\s,\\s*")));
        for (String id : parsedTasksIds) {
            Task task = this.manager.getTaskById(Integer.parseInt(id));
            resultSubtasks.add((Subtask) task);
        }
        return resultSubtasks;
    }

    public Epic getParentEpic() {
        printGetParentEpicId();
        String inputParentEpicId = this.scanner.nextLine();
        Task parentEpic = this.manager.getTaskById(Integer.parseInt(inputParentEpicId));
        return (Epic) parentEpic;
    }

    public Manager.TaskType getTaskTypeByNumber(int number) {
        switch (number) {
            case 1:
                return Manager.TaskType.TASK;
            case 2:
                return Manager.TaskType.SUBTASK;
            case 3:
                return Manager.TaskType.EPIC;
            default:
                return Manager.TaskType.TASK;
        }
    }

    public void createTask() {
        printTaskTypeSelectionMenu();
        int inputType = this.scanner.nextInt();
        Manager.TaskType tasksType = getTaskTypeByNumber(inputType);
        this.scanner.nextLine();

        printTaskNameSelectionMenu();
        String inputName = this.scanner.nextLine();

        printTaskDescriptionSelectionMenu();
        String inputDescription = this.scanner.nextLine();

        Task newTask = null;

        switch (tasksType) {
            case TASK:
                newTask = new SimpleTask(Task.TaskStatus.NEW, inputName, inputDescription, manager.generateId());
                break;
            case EPIC:
                List<Subtask> resultSubtasks = getResultEpicSubtasks();
                newTask = new Epic(Task.TaskStatus.NEW, inputName, inputDescription, manager.generateId(), resultSubtasks);
                break;
            case SUBTASK:
                Epic parentEpic = getParentEpic();
                newTask = new Subtask(Task.TaskStatus.NEW, inputName, inputDescription, manager.generateId(), parentEpic.getId());
                break;
        }

        manager.addTask(tasksType, newTask);
        printTaskSuccessAdd();
    }

    public void exitAppWithCode(int code) {
        System.out.println("Вы вышли из приложения");
        System.exit(code);
    }
}
