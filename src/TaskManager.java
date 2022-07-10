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
        task.setid(++taskid);
        tasks.put(task.getid(), task);
    }

    public List<Task> getTasks() {
        Collection<Task> values = tasks.values();
        return new ArrayList<>(values);
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getid())) {
            tasks.replace(task.getid(), task);
        }
    }

    public Task getTaskByid(int id) {
        Task value = tasks.get(id);
        return value;
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void addEpic(Epic epic) {
        epic.setid(++taskid);
        epics.put(taskid, epic);
    }

    public List<Epic> getEpics() {
        Collection<Epic> values = epics.values();
        return new ArrayList<>(values);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getid())) {
            Epic tempEpic = epics.get(epic.getid());
            tempEpic.setTitle(epic.getTitle());
            tempEpic.setDescription(epic.getDescription());
        }
    }

    public void removeEpic(int id) {
        Epic epic = epics.get(id);
        for (Subtask subtask : epic.getSubtasks()) {
            subtasks.remove(subtask.getid());
        }
        epics.remove(id);
    }

    public ArrayList<Subtask> getSubtasksOfEpic(int id) {
        ArrayList<Subtask> subtasks = epics.get(id).getSubtasks();
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtask.setid(++taskid);
        subtasks.put(taskid, subtask);
        subtask.getEpic().addSubtask(subtask);
        updateEpicStatus(subtask.getEpic());
    }

    public List<Subtask> getSubtasks() {
        Collection<Subtask> values = subtasks.values();
        return new ArrayList<>(values);
    }

    public Subtask getSubtaskByid(int id) {
        Subtask value = subtasks.get(id);
        return value;
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getid())) {
            subtask.getEpic().removeSubtask(subtasks.get(subtask.getid()));
            subtasks.replace(subtask.getid(), subtask);
            subtask.getEpic().addSubtask(subtask);
            updateEpicStatus(subtask.getEpic());
        }
    }

    public void removeSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        subtask.getEpic().removeSubtask(subtask);
        subtasks.remove(id);
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
                    subtasks.remove(subtask.getid());
                }
            }
        }
        epics = new HashMap<>();
    }

    public void clearSubtasks() {
        subtasks = new HashMap<>();
        for (Epic epic : epics.values()) {
            epic.removeSubtasks();
            updateEpicStatus(epic);
        }
    }
}
