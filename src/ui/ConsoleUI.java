package ui;

import dto.CreateEpicDto;
import dto.CreateTaskDto;
import model.*;
import service.TaskService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;
    private final TaskService taskService;
    private boolean isRunnable = true;


    public ConsoleUI(Scanner scanner, TaskService taskService) {
        this.scanner = scanner;
        this.taskService = taskService;
    }

    public void run() {
        while (isRunnable) {
            printMainMenu();
            int choiceMenuNumber = choiceInteger();
            handleMainMenuChoice(choiceMenuNumber);
        }
    }

    public void printMainMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. 📋 Показать все задачи");
        System.out.println("2. 🔍 Найти задачу по ID");
        System.out.println("3. ➕ Создать обычную задачу");
        System.out.println("4. 🎯 Создать эпик");
        System.out.println("5. 📝 Создать подзадачу");
        System.out.println("6. ✏️  Обновить задачу");
        System.out.println("7. 🗑️  Удалить задачу");
        System.out.println("8. 💾 Сохранить задачи в файл");
        System.out.println("9. 📤 Загрузить задачи из файла");
        System.out.println("0. ❌ Выход");
        System.out.print("Выберите действие: ");
    }

    public int choiceInteger() {
        while (true) {
            try {
                String input = this.scanner.nextLine();
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException err) {
                System.out.println("Номер введен некорректно");
            }
        }
    }

    public void handleMainMenuChoice(int choiceNumber) {
        switch (choiceNumber) {
            case 1 -> showAllTasks();
            case 2 -> findTaskById();
            case 3 -> createSimpleTask();
            case 4 -> createEpic();
            case 5 -> createSubtask();
            case 6 -> updateTask();
            case 7 -> deleteTask();
            case 8 -> saveTasks();
            case 9 -> loadTasks();
            case 0 -> exit();
            default -> System.out.println("⚠️  Неверный выбор! Попробуйте снова.");
        }
    }


    private void loadTasks() {

    }

    private void saveTasks() {

    }

    private void showEpicSubtasks() {
    }

    private void deleteTask() {
    }

    private void updateTask() {
    }

    private void createSubtask() {
        System.out.println("\n📝 СОЗДАНИЕ ПОДЗАДАЧИ");

        List<Epic> epics = taskService.getAllEpics();

        if(epics.isEmpty()) {
            System.out.println("❌ Нет доступных эпиков.");
            System.out.println("Сначала создайте эпик через пункт меню 4.");
            return;
        }

        System.out.println("\n🎯 Список текущих эпиков:");
        epics.stream().forEach(epic -> System.out.println("   - " + epic));
        System.out.print("Введите id родительского эпика: ");
        int parentEpicId = choiceInteger();

        System.out.print("Введите название: ");
        String name = this.scanner.nextLine();

        System.out.print("Введите описание: ");
        String description = this.scanner.nextLine();

        TaskStatus status = selectStatus();

        CreateTaskDto dto = new CreateTaskDto(name, description, status, parentEpicId);
        Subtask newSubtask = (Subtask) taskService.createTask(dto);

        System.out.println("Создана подзадача: " + newSubtask);
    }

    private void createEpic() {
        System.out.println("\n🎯 СОЗДАНИЕ ЭПИКА");

        System.out.print("Введите название эпика:");
        String name = scanner.nextLine();

        System.out.print("Введите описание эпика:");
        String description = scanner.nextLine();

        CreateEpicDto dto = new CreateEpicDto(name, description);
        Epic newEpic = taskService.createEpic(dto);

        System.out.println("Создан эпик: " + newEpic);
    }

    private void createSimpleTask() {
        System.out.println("\n➕ СОЗДАНИЕ ОБЫЧНОЙ ЗАДАЧИ");

        System.out.print("Введите название: ");
        String name = this.scanner.nextLine();

        System.out.print("Введите описание: ");
        String description = this.scanner.nextLine();

        TaskStatus status = selectStatus();

        CreateTaskDto dto = new CreateTaskDto(name, description, status);
        Task newTask = taskService.createTask(dto);

        System.out.println("Задача создана: " + newTask);
    }

    private void findTaskById() {
        System.out.print("\n🔍 Введите ID задачи: ");
        int id = choiceInteger();

        Task task = taskService.getTaskById(id);
        if (task != null) {
            System.out.println("✅ Найдена задача:");
            System.out.println(task);
        } else {
            System.out.println("❌ Задача с ID " + id + " не найдена.");
        }
    }

    private void showAllTasks() {
        System.out.println("\n=== ВСЕ ЗАДАЧИ ===");

        System.out.println("📋 Обычные задачи:");
        List<Task> simpleTasks = taskService.getByType(SimpleTask.class);
        if (simpleTasks.isEmpty()) {
            System.out.println("Нет задач");
        } else {
            simpleTasks.stream().forEach(task -> System.out.println(task));
        }

        System.out.println("\n🎯 Эпики:");
        List<Task> epics = taskService.getByType(Epic.class);
        if (epics.isEmpty()) {
            System.out.println("Нет эпиков");
        } else {
            epics.stream()
                .forEach(epic -> {
                    System.out.println(epic);

                    List<Subtask> subtasks = taskService.getSubtasksByEpicId(epic.getId());
                    if (subtasks.isEmpty()) {
                        System.out.println("    Подзадачи:");
                        subtasks.stream().forEach(subtask -> System.out.println("  -" + subtask));
                    }
                });
        }

        System.out.println("\n📝 Подзадачи:");
        List<Task> subtasks = taskService.getByType(Subtask.class);
        if(subtasks.isEmpty()) {
            System.out.println("Нет подзадач");
        } else {
            subtasks.stream()
                    .forEach(subtask -> System.out.println(subtask));
        }


        System.out.println("\n📊 Итого: " + taskService.getAllTasks().size() + " задач");
    }

    public TaskStatus selectStatus() {
        System.out.println("Выберите статус:");
        System.out.println("1. Новая");
        System.out.println("2. В работе");
        System.out.println("3. Выполнена");
        System.out.print("Ваш выбор: ");
        int choise = this.scanner.nextInt();

        return switch (choise) {
            case 1 -> TaskStatus.NEW;
            case 2 -> TaskStatus.IN_PROGRESS;
            case 3 -> TaskStatus.DONE;
            default -> TaskStatus.NEW;
        };
    }

    private void exit() {
        System.out.println("Вы вышли из проложения");
        System.exit(0);
    }

}
