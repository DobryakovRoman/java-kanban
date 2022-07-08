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

    public String tasksToString() {
        String result = "";
        for (Task task : tasks.values()) {
            result += task.toString() + '\n';
        }
        return result;
    }

    public List<Task> getTasks() {
        Collection<Task> values = tasks.values();
        return new ArrayList<>(values);
    }

    public void updateTask(int ID, Task task) {
        if (tasks.replace(ID, task) == null) {
            System.out.println("ID задачи отсутствует. Задача не обновлена");
        }
    }

    public Task getTaskByID(int ID) {
        Task value = tasks.get(ID);
        return value;
    }

    public void removeTask(int ID) {
        tasks.remove(ID);
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

    /*public Epic getLastCreatedEpic() {
        return epics.get(epics.keySet().toArray()[epics.size() - 1]);
    }*/

    public List<Epic> getEpics() {
        Collection<Epic> values = epics.values();
        return new ArrayList<>(values);
    }

    public Epic getEpicByID(int ID) {
        Epic value = epics.get(ID);
        return value;
    }

    public void updateEpic(int ID, Epic epic) {
        if (epics.get(ID) != null) {
            for (Subtask subtask : epics.get(ID).getSubtasks()) {
                epic.addSubtask(subtask);
            }
            epics.replace(ID, epic);
        } else {
            System.out.println("ID Эпика отсутствует. Эпик не обновлён");
        }
    }

    public void removeEpic(int ID) {
        Epic epic = epics.get(ID);
        for (Subtask subtask : epic.getSubtasks()) {
            subtasks.remove(subtask.getTaskID());
        }
        epics.remove(ID);
    }

    public void addSubtask(Subtask subtask) {
        subtask.setTaskID(++this.taskID);
        subtasks.put(this.taskID, subtask);
        updateEpicStatus(subtask.getEpic());
    }

    public String subtasksToString() {
        String result = "";
        for (Task task : subtasks.values()) {
            result += task.toString() + '\n';
        }
        return result;
    }

    public List<Subtask> getSubtasks() {
        Collection<Subtask> values = subtasks.values();
        return new ArrayList<>(values);
    }

    public Subtask getSubtaskByID(int ID) {
        Subtask value = subtasks.get(ID);
        return value;
    }

    public void updateSubtask(int ID, Subtask subtask) {
        subtask.getEpic().removeSubtask(subtasks.get(ID));
        if (subtasks.replace(ID, subtask) == null) {
            System.out.println("ID Подзадачи отсутствует. Подзадача не обновлёна.");
        }
        updateEpicStatus(subtask.getEpic());
    }

    public void removeSubtask(int ID) {
        Subtask subtask = subtasks.get(ID);
        subtask.getEpic().removeSubtask(subtask);
        subtasks.remove(ID);
    }

    private void updateEpicStatus(Epic epic) {
        if (epic.getSubtasks() == null) {
            epic.setStatus("NEW");
            return;
        }
        boolean statusNew = false;
        boolean statusProgress = false;
        boolean statusDone = false;
        for (Subtask subtask : epic.getSubtasks()) {
            statusNew = subtask.getStatus().equals("NEW");
            statusProgress = subtask.getStatus().equals("IN_PROGRESS");
            statusDone = subtask.getStatus().equals("DONE");
        }
        if (!statusProgress && !statusNew) {
            epic.setStatus("DONE");
            return;
        } else if (!statusProgress && !statusDone) {
            epic.setStatus("NEW");
            return;
        } else {
            epic.setStatus("IN_PROGRESS");
            return;
        }
    }

    public void clear() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.taskID = 0;
    }
}
