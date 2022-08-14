package Kanban.service;

import java.util.*;

import Kanban.constants.Status;
import Kanban.task.*;

public class InMemoryTaskManager implements TaskManager {
    private int taskid;
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private HistoryManager<Task> history = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
        taskid = 0;
    }

    @Override
    public void addTask(Task task) {
        task.setid(++taskid);
        tasks.put(task.getid(), task);
    }

    @Override
    public List<Task> getTasks() {
        Collection<Task> values = tasks.values();
        return new ArrayList<>(values);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getid())) {
            tasks.replace(task.getid(), task);
        }
    }

    @Override
    public Task getTaskById(int id) {
        history.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
        history.remove(id);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setid(++taskid);
        epics.put(taskid, epic);
    }

    @Override
    public List<Epic> getEpics() {
        Collection<Epic> values = epics.values();
        return new ArrayList<>(values);
    }

    @Override
    public Epic getEpicById(int id) {
        history.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getid())) {
            Epic tempEpic = epics.get(epic.getid());
            tempEpic.setTitle(epic.getTitle());
            tempEpic.setDescription(epic.getDescription());
        }
    }

    @Override
    public void removeEpic(int id) {
        Epic epic = epics.get(id);
        for (int subtask : epic.getSubtasks()) {
            subtasks.remove(subtask);
            history.remove(subtask);
        }
        epics.remove(id);
        history.remove(id);
    }

    @Override
    public ArrayList<Subtask> getSubtasksOfEpic(int id) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Integer subtask : epics.get(id).getSubtasks()) {
            subtasks.add(this.subtasks.get(subtask));
        }
        return subtasks;
    }

    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setid(++taskid);
        subtasks.put(taskid, subtask);
        epics.get(subtask.getEpicid()).addSubtask(subtask);
        updateEpicStatus(epics.get(subtask.getEpicid()));
    }

    @Override
    public List<Subtask> getSubtasks() {
        Collection<Subtask> values = subtasks.values();
        return new ArrayList<>(values);
    }

    @Override
    public Subtask getSubtaskByid(int id) {
        history.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getid())) {
            Subtask tempSubtask = subtasks.get(subtask.getid());
            tempSubtask.setTitle(subtask.getTitle());
            tempSubtask.setDescription(subtask.getDescription());
            tempSubtask.setStatus(subtask.getStatus());
            updateEpicStatus(epics.get(subtask.getEpicid()));
        }
    }

    @Override
    public void removeSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        epics.get(subtask.getEpicid()).removeSubtask(subtask);
        subtasks.remove(id);
        history.remove(id);
        updateEpicStatus(epics.get(subtask.getEpicid()));
    }

    @Override
    public void clear() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        taskid = 0;
        history = Managers.getDefaultHistory();
    }

    @Override
    public void clearTasks() {
        for (Integer taskId : tasks.keySet()) {
            history.remove(taskId);
        }
        tasks = new HashMap<>();
    }

    @Override
    public void clearEpics() {
        for (Epic epic : epics.values()) {
            if (!epic.getSubtasks().isEmpty()) {
                for (Integer subtask : epic.getSubtasks()) {
                    subtasks.remove(subtask);
                    history.remove(subtask);
                }
            }
            history.remove(epic.getid());
        }
        epics = new HashMap<>();
    }

    @Override
    public void clearSubtasks() {
        for (Integer subtaskId : subtasks.keySet()) {
            history.remove(subtaskId);
        }
        subtasks = new HashMap<>();
        for (Epic epic : epics.values()) {
            epic.removeSubtasks();
            updateEpicStatus(epic);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history.getHistory());
    }

    private void updateEpicStatus(Epic epic) {
        if (epic.getSubtasks().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        boolean statusNew = false;
        boolean statusProgress = false;
        boolean statusDone = false;
        for (Integer subtask : epic.getSubtasks()) {
            switch (subtasks.get(subtask).getStatus()) {
                case NEW:
                    statusNew = true;
                    break;
                case IN_PROGRESS:
                    statusProgress = true;
                    break;
                case DONE:
                    statusDone = true;
                    break;
            }
        }
        if (!statusProgress && !statusNew) {
            epic.setStatus(Status.DONE);
        } else if (!statusProgress && !statusDone) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
