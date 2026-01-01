package model;

import java.io.Serializable;

public abstract class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    protected final int id;
    protected String name;
    protected String description;
    protected TaskStatus status = TaskStatus.NEW;

    protected Task(TaskStatus status, String name, String description, int id) {
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

    public int getId() {
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