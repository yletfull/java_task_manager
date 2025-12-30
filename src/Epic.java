import java.util.*;

public class Epic extends Task {
    private static final long serialVersionUID = 1L;

    private List<Subtask> subtasks;
    public Epic(TaskStatus status, String name, String description, int id, List<Subtask> subtasks) {
        super(status, name, description, id);
        this.subtasks = subtasks;
    }

    @Override
    public String toString() {
        return super.toString() + "\n Подзадачи: " + (subtasks == null ? "[]" : subtasks);
    }

    @Override
    public void setStatus(TaskStatus status) {
        throw new UnsupportedOperationException("Невозможно вручную изменить статус Epic");
    }

    public void recalculateStatus() {
        if(subtasks.isEmpty()) {
            this.status = TaskStatus.NEW;
        }

        boolean isAllDone = true;
        boolean isAnyInProgress = false;

        for (Task subtask : subtasks) {
            if(subtask.getStatus() == TaskStatus.IN_PROGRESS) {
                isAnyInProgress = true;
            }
            if(subtask.getStatus() == TaskStatus.DONE) {
                isAllDone = false;
            }
        }

        if(isAllDone) {
            this.status = TaskStatus.DONE;
        } else if (isAnyInProgress) {
            this.status = TaskStatus.IN_PROGRESS;
        } else {
            this.status = TaskStatus.NEW;
        }
      }
}
