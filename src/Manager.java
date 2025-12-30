import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {
    public enum TaskType {
        TASK,
        SUBTASK,
        EPIC,
    }

    private Map<TaskType, List<Task>> tasks;
    private int lastId;

    public Manager() {
        this.tasks = new HashMap<>();
        this.lastId = 0;

//        try {
//            loadFile(Path.of("data", "tasks.bin"));
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }

    public List<Task> getTasksByType(TaskType taskType) {
        return this.tasks.getOrDefault(taskType, new ArrayList<>());
    }

    public Task getTaskById(Integer id) {
        for(TaskType taskType : this.tasks.keySet()) {
            List<Task> currentTypeTasks = this.tasks.get(taskType);
            for(Task task : currentTypeTasks) {
                if(task.getId() == id) {
                    return task;
                }
            }
        }
        return null;
    }

    public void setTasksByType(TaskType taskType, List<Task> tasks) {
        this.tasks.put(taskType, tasks);
    }

    public void removeAllTasksByType(TaskType taskType) {
        this.tasks.remove(taskType);
    }

    public void addTask(TaskType taskType, Task task) {
        List<Task> list = this.tasks.get(taskType);

        if (list == null) {
            list = new ArrayList<>();
            this.tasks.put(taskType, list);
        }
        list.add(task);
    }


    public Map<TaskType, List<Task>> getAllTasks() {
        return this.tasks;
    }

    public void saveFile(Path file) throws IOException {
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent); // создаст /data или data если нет
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            out.writeObject(this.tasks);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFile(Path file) throws IOException, ClassNotFoundException {
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            this.tasks = (Map<Manager.TaskType, List<Task>>) in.readObject();
        }
    }

    public int generateId() {
        return this.lastId++;
    }
}
