package model;

public class SimpleTask extends Task {
    private static final long serialVersionUID = 1L;

    public SimpleTask(TaskStatus status, String name, String description, int id) {
        super(status, name, description, id);
    }
}
