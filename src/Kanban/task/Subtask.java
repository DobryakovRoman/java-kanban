package Kanban.task;

import Kanban.constants.Status;
import Kanban.constants.TaskType;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    private int epicid;

    public Subtask(String title, String description) {
        super(title, description, TaskType.SUBTASK);
    }

    public Subtask(String title, String description, int id, Status status, TaskType taskType, int epicid) {
        super(title, description, id, status, taskType);
        this.epicid = epicid;
    }

    public Subtask(String title, String description, int epicid, int duration, LocalDateTime startTime) {
        super(title, description, startTime, duration);
        this.epicid = epicid;
    }

    public Subtask(String title, String description, LocalDateTime startTime, int duration) {
        super(title, description, TaskType.SUBTASK, startTime, duration);
    }

    public Subtask(String title, String description, int id, Status status, TaskType taskType, int duration, LocalDateTime startTime, int epicid) {
        super(title, description, id, status, taskType, duration, startTime);
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
        return "Subtask{" +
                "epicid=" + epicid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", taskType=" + taskType +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicid == subtask.epicid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicid);
    }
}
