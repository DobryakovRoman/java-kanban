package Kanban.task;

public class Subtask extends Task {
    private int epicid;

    public Subtask(String title, String description) {
        super(title, description);
    }

    public int getEpicid() {
        return epicid;
    }

    public void setEpicid(int epicid) {
        this.epicid = epicid;
    }

    @Override
    public String toString() {
        return "Kanban.task.Subtask{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
