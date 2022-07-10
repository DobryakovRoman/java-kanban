public class Subtask extends Task {
    private Epic epic;

    public Subtask(String title, String description) {
        super(title, description);
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskid=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
