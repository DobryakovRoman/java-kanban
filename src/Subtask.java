public class Subtask extends Task {
    private Epic epic;

    public Subtask(Epic epic) {
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }
}
