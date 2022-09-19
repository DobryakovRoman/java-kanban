package Kanban.task;
import Kanban.constants.Status;
import Kanban.constants.TaskType;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected String title;
    protected String description;
    protected int id;
    protected Status status;
    protected TaskType taskType;
    protected int duration;
    protected LocalDateTime startTime;

    public Task() {
        title = "";
        description = "";
        id = 0;
        status = Status.NEW;
        taskType = TaskType.TASK;
        duration = 0;
        startTime = null;
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        status = Status.NEW;
        id = 0;
        taskType = TaskType.TASK;
        duration = 0;
        startTime = null;
    }

    public Task(String title, String description, TaskType taskType) {
        this.title = title;
        this.description = description;
        status = Status.NEW;
        id = 0;
        this.taskType = taskType;
        duration = 0;
        startTime = null;
    }

    public Task(String title, String description, int id, Status status, TaskType taskType) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.taskType = taskType;
        duration = 0;
        startTime = null;
    }

    public Task(String title, String description, int id, Status status, TaskType taskType, int duration, LocalDateTime startTime) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.taskType = taskType;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String title, String description, LocalDateTime startTime, int duration) {
        this(title, description);
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String title, String description, TaskType taskType, LocalDateTime startTime, int duration) {
        this(title, description, taskType);
        this.duration = duration;
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && duration == task.duration && title.equals(task.title) && description.equals(task.description) && status == task.status && taskType == task.taskType && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, status, taskType, duration, startTime);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", taskType=" + taskType +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }
}
