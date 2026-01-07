package dto;

import model.TaskStatus;

public class CreateTaskDto {
    private String name;
    private String description;
    private TaskStatus status;
    private Integer epicId;

    public CreateTaskDto(String name, String description, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.epicId = null;
    }

    public CreateTaskDto(String name, String description, TaskStatus status, Integer epicId) {
        this.name = name;
        this.description = description;
        this.status = status;
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

    public boolean hasEpicId() {
        return epicId != null;
    }

    @Override
    public String toString() {
        return String.format("CreateTaskDto{name='%s', description='%s', status='%s', epicId='%s'}",
                name, description, status, epicId);
    }
}
