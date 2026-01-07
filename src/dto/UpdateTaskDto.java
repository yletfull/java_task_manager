package dto;

import model.TaskStatus;

public class UpdateTaskDto {
    private String name;
    private String description;
    private TaskStatus status;
    private Integer id;
    private Integer epicId;

    public UpdateTaskDto() {
    }

    public Integer getEpicId() {
        return epicId;
    }

    public String getName() {
        return name;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean hasEpicId() {
        return epicId != null;
    }
}
