package Kanban.task;

import Kanban.constants.Status;
import Kanban.constants.TaskType;

public class Subtask extends Task {
    private int epicid;

    public Subtask(String title, String description) {
        super(title, description, TaskType.SUBTASK);
    }

    public Subtask(String title, String description, int id, Status status, TaskType taskType, int epicid) {
        super(title, description, id, status, taskType);
        this.epicid = epicid;
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
