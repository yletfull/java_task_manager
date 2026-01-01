package model;

public class Subtask extends Task {
    private static final long serialVersionUID = 1L;

    private int parentEpicId;
    public Subtask(TaskStatus status, String name, String description, int id, int parentEpicId) {
        super(status, name, description, id);
        this.parentEpicId = parentEpicId;
    }

    public Subtask(Task task, Epic parentEpic) {
        super(task.getStatus(), task.getName(), task.getDescription(), task.getId());
        this.parentEpicId = parentEpic.getId();
    }

    public void setParentEpicId(int parentEpicId) {
        this.parentEpicId = parentEpicId;
    }

    public int getParentEpicId() {
        return this.parentEpicId;
    }
}
