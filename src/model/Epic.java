package model;

import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasksIds;

    public Epic(TaskStatus status, String name, String description, int id) {
        super(status, name, description, id);
    }

    public Epic(TaskStatus status, String name, String description, int id, List<Integer> subtasksIds) {
        super(status, name, description, id);
        this.subtasksIds = subtasksIds;
    }

    @Override
    public String toString() {
        return super.toString() + "\n Индентификаторы подзадач: " + (subtasksIds == null ? "[]" : subtasksIds);
    }

    @Override
    public void setStatus(TaskStatus status) {
        throw new UnsupportedOperationException("Невозможно вручную изменить статус Epic");
    }

    public void addSubtaskId(int id) {
        if(!subtasksIds.contains(id)) {
            subtasksIds.add(id);
        }
    }


//    public void recalculateStatus() {
//        if (subtasks.isEmpty()) {
//            this.status = TaskStatus.NEW;
//        }
//
//        boolean isAllDone = true;
//        boolean isAnyInProgress = false;
//
//        for (Task subtask : subtasks) {
//            if (subtask.getStatus() == TaskStatus.IN_PROGRESS) {
//                isAnyInProgress = true;
//            }
//            if (subtask.getStatus() == TaskStatus.DONE) {
//                isAllDone = false;
//            }
//        }
//
//        if (isAllDone) {
//            this.status = TaskStatus.DONE;
//        } else if (isAnyInProgress) {
//            this.status = TaskStatus.IN_PROGRESS;
//        } else {
//            this.status = TaskStatus.NEW;
//        }
//    }
}
