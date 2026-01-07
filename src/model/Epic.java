package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasksIds = new ArrayList<>();

    public Epic(TaskStatus status, String name, String description, int id) {
        super(status, name, description, id);
    }

    public Epic(TaskStatus status, String name, String description, int id, List<Integer> subtasksIds) {
        super(status, name, description, id);
        this.subtasksIds = subtasksIds;
    }

    public List<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    @Override
    public String toString() {
        return super.toString() + "\n Индентификаторы подзадач: " + (subtasksIds == null ? "[]" : subtasksIds);
    }


    public void addSubtaskId(int id) {
        if(!subtasksIds.contains(id)) {
            subtasksIds.add(id);
        }
    }
}
