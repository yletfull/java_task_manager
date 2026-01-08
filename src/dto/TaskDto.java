package dto;

import model.TaskStatus;

public class TaskDto {
    private Integer id;
    private String name;
    private String description;
    private TaskStatus status;
    private Integer epicId;

    public TaskDto(String name, String description, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.epicId = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    public TaskDto(String name, String description, TaskStatus status, Integer epicId) {
        this(name, description, status);
        this.epicId = epicId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public boolean isValid() {
        return name != null && !name.trim().isEmpty() && description != null && status != null;
    }

    public boolean isNew() {
        return id != null;
    }

    public boolean hasEpicId() {
        return epicId != null;
    }

    @Override
    public String toString() {
        return String.format("CreateTaskDto{name='%s', description='%s', status='%s', epicId='%s'}", name, description, status, epicId);
    }
}
