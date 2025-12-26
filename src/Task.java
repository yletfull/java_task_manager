import java.io.Serializable;

public class Task implements Serializable {
    public enum TaskStatus {
        NEW,
        IN_PROGRESS,
        DONE,
    }

    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String description;
    private TaskStatus status = TaskStatus.NEW;


    public Task() {
    }

    public Task(TaskStatus status, String name, String description, int id) {
        this.status = status;
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getTaskId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "\n ____" + "\n Имя: " + this.name + "\n Описание:" + this.description + "\n Id:" + this.id + "\n Status:" + this.status;
    }
}
