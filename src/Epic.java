import java.util.*;

public class Epic extends Task {
    private List<Task> subtasks;

    public Epic(TaskStatus status, String name, String description, int id, List<Integer> subtasksIds, List<Task> tasks) {
        super(status, name, description, id);
        this.subtasks = new ArrayList<>();
        Set<Integer> ids = new HashSet<>(subtasksIds);
        for (Task task : tasks) {
            if (ids.contains(task.getTaskId())) {
                subtasks.add(task);
            }
        }
    }
}
