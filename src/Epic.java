import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic() {
        super();
    }

    public Epic(String title, String description) {
        super(title, description);
        subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public void removeSubtasks() {
        subtasks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskid=" + id +
                ", status='" + status + '\'' +
                ", subtasks=" + subtasks +
                '}';
    }
}
