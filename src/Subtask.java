public class Subtask extends Task {
    private Epic epic;

    public Subtask(String title, String description, int taskID, Epic epic) {
        super(title, description, taskID);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
