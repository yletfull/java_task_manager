
public class Task {
    public enum TaskStatus {
        NEW,
        IN_PROGRESS,
        DONE,
    }

    private int id;
    private String name;
    private String description;
    private TaskStatus status = TaskStatus.NEW;


    public Task() {
    }

    public Task(TaskStatus status, String name, String description, int id) {
        this.status = status;
    }


    public TaskStatus getStatus () {
        return this.status;
    }

    public void setStatus (TaskStatus status) {
        this.status = status;
    }
}
