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
    }

    public List<Task> getTasksByType(TaskType taskType) {
        return this.tasks.getOrDefault(taskType, new ArrayList<>());
    }

    public void setTasksByType(TaskType taskType, List<Task> tasks) {
        this.tasks.put(taskType, tasks);
    }

    public void removeAllTasksByType(TaskType taskType) {
        this.tasks.remove(taskType);
    }

    public void addTask(TaskType taskType, Task task) {
        List<Task> list = this.tasks.get(taskType);

        if(list == null) {
            list = new ArrayList<>();
            this.tasks.put(taskType, list);
        }
        list.add(task);
    }

    public TaskType getTaskTypeByNumber (int number) {
        switch (number) {
            case 1:
                return TaskType.TASK;
            case 2:
                return TaskType.SUBTASK;
            case 3:
                return TaskType.EPIC;
            default:
                return TaskType.TASK;
        }
    };

    public int generateId () {
        return ++this.lastId;
    }
}
