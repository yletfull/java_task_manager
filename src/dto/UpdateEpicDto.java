package dto;

import model.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public class UpdateEpicDto {
    private List<Integer> subtasksIds = new ArrayList<>();
    private String name;
    private String description;
    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }

    private Integer id;

    public UpdateEpicDto() {
    }

    public List<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void setSubtasksIds(List<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
