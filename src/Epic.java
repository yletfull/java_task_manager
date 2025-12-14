import java.util.*;

public class Epic extends Task {
    private List<Task> subtasks;
    private static final long serialVersionUID = 1L;

    public Epic(TaskStatus status, String name, String description, int id, List<Task> subtasks) {
        super(status, name, description, id);
        this.subtasks = subtasks;
    }

    @Override
    public String toString() {
        return super.toString() + "\n Подзадачи: " + (subtasks == null ? "[]" : subtasks);
    }
}
