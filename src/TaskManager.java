import java.util.ArrayList;
import java.util.Collection;
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

    public void addTask(Task task) {
        task.setTaskID(++this.taskID);
        tasks.put(task.getTaskID(), task);
    }

    public ArrayList<Task> getTasks() {
        return (ArrayList<Task>) tasks.values();
    }

    public String tasksToString() {
        String result = "";
        for (Task task : tasks.values()) {
            result += task.toString() + '\n';
        }
        return result;
    }

    public void addEpic(Epic epic) {
        epic.setTaskID(++this.taskID);
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

    public List<Epic> getEpics() {
        Collection<Epic> values = epics.values();
        return new ArrayList<>(values);
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

    public ArrayList<Subtask> getSubtasks() {
        return (ArrayList<Subtask>) subtasks.values();
    }

    public void clear() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.taskID = 0;
    }


}
