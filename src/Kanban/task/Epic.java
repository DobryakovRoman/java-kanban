package Kanban.task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasks = new ArrayList<>();

    public Epic() {
        super();
    }

    public Epic(String title, String description) {
        super(title, description);
        subtasks = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasks() {
        return new ArrayList<>(subtasks);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask.getid());
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask.getid());
    }

    public void removeSubtasks() {
        subtasks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Kanban.task.Epic{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                ", subtasks=" + subtasks +
                '}';
    }
}
