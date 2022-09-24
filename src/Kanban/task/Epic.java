package Kanban.task;

import Kanban.constants.Status;
import Kanban.constants.TaskType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Integer> subtasks = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic() {
        super();
        taskType = TaskType.EPIC;
    }

    public Epic(String title, String description) {
        super(title, description, TaskType.EPIC);
        subtasks = new ArrayList<>();
    }

    public Epic(String title, String description, int id, Status status, TaskType taskType) {
        super(title, description, id, status, taskType);
        subtasks = new ArrayList<>();
    }

    public Epic(String title, String description, int id, Status status, TaskType taskType, int duration, LocalDateTime startTime, LocalDateTime endTime) {
        super(title, description, id, status, taskType, duration, startTime);
        this.endTime = endTime;
    }

    public ArrayList<Integer> getSubtasks() {
        return new ArrayList<>(subtasks);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask.getid());
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove((Integer) subtask.getid());
    }

    public void removeSubtasks() {
        subtasks = new ArrayList<>();
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", endTime=" + endTime +
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
        Epic epic = (Epic) o;
        return subtasks.equals(epic.subtasks) && Objects.equals(endTime, epic.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasks, endTime);
    }
}
