public class Task {
    protected String title;
    protected String description;
    protected int id;
    protected String status;

    public Task() {
        title = "";
        description = "";
        id = 0;
        status = "NEW";
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        status = "NEW";
        id = 0;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskID=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
