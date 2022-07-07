import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;

    public Epic() {

    }

    public Epic(String title, String description, int taskID) {
        super(title, description, taskID);
        this.subtasks = new ArrayList<>();
    }

    public void addSubtask(Subtask subtask) {
        this.subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        this.subtasks.remove(subtask);
    }

    @Override
    public String toString() {
        return super.toString() +
                "Epic{" +
                "subtasks=" + subtasks +
                '}';
    }
}
