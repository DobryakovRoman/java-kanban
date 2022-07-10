import java.util.*;

public class TaskManager {
    private int taskid;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public TaskManager() {
        taskid = 0;
    }

    public void addTask(Task task) {
        task.setId(++taskid);
        tasks.put(task.getId(), task);
    }

    public List<Task> getTasks() {
        Collection<Task> values = tasks.values();
        return new ArrayList<>(values);
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.replace(task.getId(), task);
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
        epic.setId(++this.taskid);
        epics.put(this.taskid, epic);
    }

    public List<Epic> getEpics() {
        Collection<Epic> values = epics.values();
        return new ArrayList<>(values);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic tempEpic = epics.get(epic.getId());
            tempEpic.setTitle(epic.getTitle());
            tempEpic.setDescription(epic.getDescription());
            updateEpicStatus(epic);
        }
    }

    public void removeEpic(int ID) {
        Epic epic = epics.get(ID);
        for (Subtask subtask : epic.getSubtasks()) {
            subtasks.remove(subtask.getId());
        }
        epics.remove(ID);
    }

    public ArrayList<Subtask> getEpicsSubtasks(int epicID) {
        ArrayList<Subtask> subtasks = this.epics.get(epicID).getSubtasks();
        return subtasks;
    }

    public void addSubtask(Subtask subtask, Epic epic) {
        subtask.setId(++taskid);
        subtasks.put(taskid, subtask);
        subtask.setEpic(epic);
        epic.addSubtask(subtask);
        updateEpicStatus(subtask.getEpic());
    }

    public List<Subtask> getSubtasks() {
        Collection<Subtask> values = subtasks.values();
        return new ArrayList<>(values);
    }

    public Subtask getSubtaskByID(int ID) {
        Subtask value = subtasks.get(ID);
        return value;
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtask.getEpic().removeSubtask(subtasks.get(subtask.getId()));
            subtasks.replace(subtask.getId(), subtask);
            subtask.getEpic().addSubtask(subtask);
            updateEpicStatus(subtask.getEpic());
        }
    }

    public void removeSubtask(int ID) {
        Subtask subtask = subtasks.get(ID);
        subtask.getEpic().removeSubtask(subtask);
        subtasks.remove(ID);
        updateEpicStatus(subtask.getEpic());
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
            if (subtask.getStatus().equals("NEW")) {
                statusNew = true;
                continue;
            }
            if (subtask.getStatus().equals("IN_PROGRESS")) {
                statusProgress = true;
                continue;
            }
            if (subtask.getStatus().equals("DONE")) {
                statusDone = true;
                continue;
            }
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
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        taskid = 0;
    }

    public void clearTasks() {
        tasks = new HashMap<>();
    }

    public void clearEpics() {
        for (Epic epic : epics.values()) {
            if (epic.getSubtasks() != null) {
                for (Subtask subtask : epic.getSubtasks()) {
                    subtasks.remove(subtask.getId());
                }
            }
        }
        epics = new HashMap<>();
    }

    public void clearSubtasks() {
        subtasks = new HashMap<>();
        for (Epic epic : epics.values()) {
            for (Subtask subtask : epic.getSubtasks()) {
                epic.removeSubtask(subtask);
            }
            updateEpicStatus(epic);
        }
    }
}
