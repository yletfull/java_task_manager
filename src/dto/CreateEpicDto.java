package dto;

import model.TaskStatus;

public class CreateEpicDto {
    private String name;
    private String description;

    public CreateEpicDto(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isValid() {
        return name != null && !name.trim().isEmpty() && description != null;
    }


    @Override
    public String toString() {
        return String.format("CreateTaskDto{name='%s', description='%s'}",
                name, description);
    }
}
