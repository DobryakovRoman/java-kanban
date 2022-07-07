import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private int taskID;
    HashMap<Integer, Task> tasks;
    HashMap<Integer, Epic> epics;
    HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.taskID = 0;
    }

    public void addTask(String title, String description) {
        Task task = new Task(title, description, ++this.taskID);
        tasks.put(taskID, task);
    }

    public String tasksToString() {
        String result = "";
        for (Task task : tasks.values()) {
            result += task.toString() + '\n';
        }
        return result;
    }

    public void addEpic(String title, String description) {
        Epic epic = new Epic(title, description, ++this.taskID);
        epics.put(this.taskID, epic);
    }

    public String epicsToString() {
        String result = "";
        for (Task task : epics.values()) {
            result += task.toString() + '\n';
        }
        return result;
    }

    public Epic getLastCreatedEpic() {
        return epics.get(epics.keySet().toArray()[epics.size() - 1]);
    }

    public void addSubtask(String title, String description, Epic epic) {
        if (epic != null) {
            Subtask subtask = new Subtask(title, description, ++this.taskID, epic);
            subtasks.put(this.taskID, subtask);
            epic.addSubtask(subtask);
        } else {
            System.out.println("Подзадача не создана. Необходимо указать эпик.");
        }
    }

    public String subtasksToString() {
        String result = "";
        for (Task task : subtasks.values()) {
            result += task.toString() + '\n';
        }
        return result;
    }
}
