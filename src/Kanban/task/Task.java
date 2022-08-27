package Kanban.task;
import Kanban.constants.Status;
import Kanban.constants.TaskType;

public class Task {
    protected String title;
    protected String description;
    protected int id;
    protected Status status;
    protected TaskType taskType;

    public Task() {
        title = "";
        description = "";
        id = 0;
        status = Status.NEW;
        taskType = TaskType.TASK;
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        status = Status.NEW;
        id = 0;
        taskType = TaskType.TASK;
    }

    public Task(String title, String description, TaskType taskType) {
        this.title = title;
        this.description = description;
        status = Status.NEW;
        id = 0;
        this.taskType = taskType;
    }

    public Task(String title, String description, int id, Status status, TaskType taskType) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.taskType = taskType;
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

    @Override
    public String toString() {
        return "Kanban.task.Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
