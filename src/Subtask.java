public class Subtask extends Task {
    private Epic parentEpic;
    public Subtask(TaskStatus status, String name, String description, int id, Epic parentEpic) {
        super(status, name, description, id);
        this.parentEpic = parentEpic;
    }

    public Subtask(Task task, Epic parentEpic) {
        super(task.getStatus(), task.getName(), task.getDescription(), task.getTaskId());
    }

    public void setParentEpic(Epic parentEpic) {
        this.parentEpic = parentEpic;
    }

   public Epic getParentEpic() {
        return this.parentEpic;
   }
}
